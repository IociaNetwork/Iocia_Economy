package iocia.network.economy.utls.config;

import iocia.network.economy.utls.LogTypes;
import iocia.network.economy.utls.Logger;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Manager class for all config related files
 */
public enum Config {

    INSTANCE;

    private Map<String, File> fileMap;
    private Map<String, YamlConfiguration> yamlConfigurationMap;

    Config() {
        fileMap = new HashMap<>();
        yamlConfigurationMap = new HashMap<>();
    }

    /**
     * Used by other classes to see if a user has been initialized.
     *
     * @param configID User onfig ID
     * @return true if initialized; false if not
     */
    public static boolean isInit(String configID) {

        return (INSTANCE.fileMap.containsKey(configID) && INSTANCE.yamlConfigurationMap.containsKey(configID));

    }

    public static void removeConfig(String configID) {

        INSTANCE.fileMap.remove(configID);
        INSTANCE.yamlConfigurationMap.remove(configID);

    }

    /**
     * Allows the easy creation of new folders.
     *
     * @param folderName Name of folder
     * @param baseLocation Location of folder
     * @return true if successful; false otherwise
     */
    public static boolean createFolder(String folderName, File baseLocation) {

        File dir = new File(baseLocation, folderName);

        return dir.mkdirs();

    }

    /**
     * Initializes the given File into a YamlConfiguration file to be used for config purposes.
     * If the file does not exist, a new file will be created.
     *
     * @param configName Name of the config file
     * @param baseLocation Base folder location of plugin configs
     * @param subLocation Desired sub-folders within the base location to locate the file
     * @return true if successful; false otherwise
     */
    public static boolean createLoadConfigFile(String configName, File baseLocation, String subLocation) {

        if (INSTANCE.yamlConfigurationMap.containsKey(configName)) {
            //TODO Add config file already initialized error message.
            return false;
        }

        File config;

        if (subLocation == null) {
            config = new File(baseLocation, configName + ".yml");
        } else {
            config = new File(baseLocation + File.separator + subLocation, configName + ".yml");
        }

        if (!config.exists()) {
            try {
                config.getParentFile().mkdirs();
                config.createNewFile();
            } catch (java.io.IOException e) {
                //TODO Add create new file error message.
                return false;
            }
        }

        INSTANCE.fileMap.put(configName, config);
        INSTANCE.yamlConfigurationMap.put(configName, YamlConfiguration.loadConfiguration(config));

        return true;

    }

    /**
     * Initializes the given File into a YamlConfiguration file to be used for config purposes.
     * If the file does not exist, a new file will be created.
     *
     * @param configName Name of the config file
     * @param baseLocation Base folder location of plugin configs
     * @return true if successful; false otherwise
     */
    public static boolean createLoadConfigFile(String configName, File baseLocation) {
        return createLoadConfigFile(configName, baseLocation, null);
    }

    /**
     * Allows the creation of a config file from a "master file" located within the exported jar file.
     * This allows more control over how the initial config file is created on the server's plugin data folder.
     * For example, the ability to add comments within the config file is now possible.
     *
     * @param configName Name of the config file in the jar file. Also the name of the config file
     *                   that will be created
     * @param baseLocation Base folder location of plugin configs
     * @param subLocation Desired sub-folders within the base location to locate the file
     * @return true if successful; false otherwise
     */
    public static boolean createLoadConfigFromBase(String configName, File baseLocation, String subLocation) {

        //Checks to make sure the file has not already been initialized.
        if (INSTANCE.fileMap.containsKey(configName)) {
            //TODO Add file already initialized error
            return false;
        }

        //Creates the File object for the config file.
        File config;
        if (subLocation == null) {
            config = new File(baseLocation, configName + ".yml");
        } else {
            config = new File(baseLocation + File.separator + subLocation, configName + ".yml");
        }

        //Creates the file if it doesn't exist and copies the master copy data over to it.
        if (!config.exists()) {

            //Creates the physical file on the computer.
            try {
                //Creates any parent folders required for the config file.
                config.getParentFile().mkdirs();

                //Creates the file.
                config.createNewFile();

            } catch (java.io.IOException e) {
                //TODO Add create new file error message.
                return false;
            }

            //Loads the data coming in from the master file and copies it to the new config file.
            InputStream in = null;
            OutputStream out = null;
            try {

                in = Config.class.getClassLoader().getResourceAsStream(configName + ".yml");
                out = new FileOutputStream(config);

                if (in == null)
                    Logger.logFormatted(LogTypes.DEBUG, configName);

                byte[] buffer = new byte[1024];
                int readBytes;

                while ((readBytes = in.read(buffer)) > 0)
                    out.write(buffer, 0, readBytes);

            } catch (java.io.IOException e) {
                //TODO Add option to print stack trace
                return false;
            } finally {

                try {

                    if (in != null)
                        in.close();

                    if (out != null)
                        out.close();

                } catch (java.io.IOException e) {
                    //TODO Add option to print stack trace
                }

            }

        }

        //Load newly made config file into Maps.
        INSTANCE.fileMap.put(configName, config);
        INSTANCE.yamlConfigurationMap.put(configName, YamlConfiguration.loadConfiguration(config));

        return true;

    }

    /**
     * Allows the creation of a config file from a "master file" located within the exported jar file.
     * This allows more control over how the initial config file is created on the server's plugin data folder.
     * For example, the ability to add comments within the config file is now possible.
     *
     * @param configName Name of the config file in the jar file. Also the name of the config file
     *                   that will be created
     * @param baseLocation Base folder location of plugin configs
     * @return true if successful; false otherwise
     */
    public static boolean createLoadConfigFromBase(String configName, File baseLocation) {
        return createLoadConfigFromBase(configName, baseLocation, null);
    }

    /**
     * Allows classes to access the desired YamlConfiguration file based off of the given config ID.
     *
     * @param configID ID of the YamlConfiguration file
     * @return YamlConfiguration if successful; null if the configID does not have an associated YamlConfiguration file
     */
    public static YamlConfiguration getConfig(String configID) {
        return INSTANCE.yamlConfigurationMap.get(configID);
    }

    public static File getFile(String configID) {
        return INSTANCE.fileMap.get(configID);
    }

    /**
     * Saves the YamlConfiguration file to its appropriate system File. The configID is used to determine which file
     * to save.
     *
     * @param configID ID of the config to save
     * @return true if successful; false otherwise
     */
    public static boolean saveConfig(String configID) {

        try {
            INSTANCE.yamlConfigurationMap.get(configID).save(INSTANCE.fileMap.get(configID));
        } catch (java.io.IOException e) {
            //TODO Add save config data exception message.
            return false;
        }

        return true;

    }

}
