package com.challengezero12.marcobrugnera.challengezero12;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Util {

    public String getProperty(String key, Context context) throws IOException {
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("config.properties");
        properties.load(inputStream);
        return properties.getProperty(key);
    }


    public static ArrayList<Object> getBallProperties(Context context) throws IOException {

        ArrayList<Object> tmp = new ArrayList<>();
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("config.properties");
        properties.load(inputStream);

        //Popolo la struttura dati restituita dalla funzione
        tmp.add(Double.parseDouble(properties.getProperty("ballposx")));
        tmp.add(Double.parseDouble(properties.getProperty("ballposy")));
        tmp.add(Double.parseDouble(properties.getProperty("ballvx")));
        tmp.add(Double.parseDouble(properties.getProperty("ballvy")));
        tmp.add(Integer.parseInt(properties.getProperty("ballradius")));

        return tmp;
    }

    public static ArrayList<Integer> getBallColorProperties(Context context) throws IOException{
        ArrayList<Integer> tmp = new ArrayList<>();
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("config.properties");
        properties.load(inputStream);

        //Popolo la struttura dati restituita dalla funzione
        tmp.add(Integer.parseInt(properties.getProperty("ball_color_componentA")));
        tmp.add(Integer.parseInt(properties.getProperty("ball_color_componentR")));
        tmp.add(Integer.parseInt(properties.getProperty("ball_color_componentG")));
        tmp.add(Integer.parseInt(properties.getProperty("ball_color_componentB")));

        return tmp;
    }

    public static ArrayList<Integer> getPaddleProperties(Context context) throws IOException{
        ArrayList<Integer> tmp = new ArrayList<>();
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("config.properties");
        properties.load(inputStream);

        //Popolo la struttura dati restituita dalla funzione
        tmp.add(Integer.parseInt(properties.getProperty("paddle_l")));
        tmp.add(Integer.parseInt(properties.getProperty("paddle_t")));
        tmp.add(Integer.parseInt(properties.getProperty("paddle_r")));
        tmp.add(Integer.parseInt(properties.getProperty("paddle_b")));

        return tmp;
    }


    public static ArrayList<Integer> getPaddleColorProperties(Context context) throws IOException{
        ArrayList<Integer> tmp = new ArrayList<>();
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("config.properties");
        properties.load(inputStream);

        //Popolo la struttura dati restituita dalla funzione
        tmp.add(Integer.parseInt(properties.getProperty("paddle_color_componentA")));
        tmp.add(Integer.parseInt(properties.getProperty("paddle_color_componentR")));
        tmp.add(Integer.parseInt(properties.getProperty("paddle_color_componentG")));
        tmp.add(Integer.parseInt(properties.getProperty("paddle_color_componentB")));

        return tmp;
    }

    public static ArrayList<Integer> getBricksAndWallProperties (Context context) throws IOException {
        ArrayList<Integer> tmp = new ArrayList<>();
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("config.properties");
        properties.load(inputStream);

        //Popolo la struttura dati restituita dalla funzione
        tmp.add(Integer.parseInt(properties.getProperty("number_of_lines")));
        tmp.add(Integer.parseInt(properties.getProperty("bricks_per_line")));
        tmp.add(Integer.parseInt(properties.getProperty("brick_width")));
        tmp.add(Integer.parseInt(properties.getProperty("brick_height")));

        return tmp;
    }



    public static ArrayList<Integer> getBrickColorProperties(Context context) throws IOException{
        ArrayList<Integer> tmp = new ArrayList<>();
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("config.properties");
        properties.load(inputStream);

        tmp.add(Integer.parseInt(properties.getProperty("brick_color_componentA")));
        tmp.add(Integer.parseInt(properties.getProperty("brick_color_componentR")));
        tmp.add(Integer.parseInt(properties.getProperty("brick_color_componentG")));
        tmp.add(Integer.parseInt(properties.getProperty("brick_color_componentB")));

        return tmp;
    }




}
