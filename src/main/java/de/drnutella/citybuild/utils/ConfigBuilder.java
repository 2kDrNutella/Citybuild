package de.drnutella.citybuild.utils;

import de.drnutella.citybuild.Citybuild;
import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ConfigBuilder {

    private final File file;
    private final YamlConfiguration config;

    public ConfigBuilder(String path, String filename){

        File dir = new File(path);

        if(!dir.exists()){
            dir.mkdirs();
        }

        this.file = new File(dir, filename);

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public ConfigBuilder(String path, String filename, String templatePath, String templateFileName){
        File dir = new File(path);

        if(!dir.exists()){
            dir.mkdirs();
        }

        this.file = new File(dir, filename);

        //Checks whether the file exists, otherwise the template is taken from the resources directory.

        if(!file.exists()){
            final InputStream inputStream = Citybuild.getInstance().getResource(templatePath + templateFileName + ".yml");
            final File file = new File(path, filename);

            try {
                FileUtils.copyInputStreamToFile(inputStream, file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public ConfigBuilder(Plugin plugin, String path, String filename, String templatePath, String templateFileName){
        File dir = new File(path);

        if(!dir.exists()){
            dir.mkdirs();
        }

        this.file = new File(dir, filename);

        //Checks whether the file exists, otherwise the template is taken from the resources directory.

        if(!file.exists()){
            final InputStream inputStream = plugin.getResource(templatePath + templateFileName + ".yml");
            final File file = new File(path, filename);

            try {
                FileUtils.copyInputStreamToFile(inputStream, file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public File getFile() {
        return file;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void save(){
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
