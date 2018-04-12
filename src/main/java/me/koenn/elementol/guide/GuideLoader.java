package me.koenn.elementol.guide;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;

public class GuideLoader {

    public static GuideLoader INSTANCE;

    private Page mainPage;

    public GuideLoader() {
        if (INSTANCE != null) {
            throw new RuntimeException("ElementalGuideLoader already exists!");
        }
        INSTANCE = this;
    }

    public void load() {
        this.mainPage = this.parsePages(this.parseFile("assets/elementol/guide/main.json"))[0];
    }

    public Page[] parsePages(JSONObject pageObject) {
        if (!pageObject.containsKey("type")) {
            throw new IllegalArgumentException("Invalid JSONObject \'" + pageObject + "\'");
        }

        String type = (String) pageObject.get("type");
        switch (type) {
            case "listPage":
                String listTitle = (String) pageObject.get("title");
                JSONObject[] listItems = this.parseJSONArray((JSONArray) pageObject.get("listItems"));
                return new Page[]{new ListPage(listTitle, this.parseListPageItems(listItems))};
            case "pages":
                JSONObject[] pages = this.parseJSONArray((JSONArray) pageObject.get("pages"));
                return this.parsePages(pages);
            case "textPage":
                String pageTitle = (String) pageObject.get("title");
                String pageText = (String) pageObject.get("text");
                return new Page[]{new TextPage(pageTitle, pageText)};
            case "recipePage":
                Item item = Item.REGISTRY.getObject(new ResourceLocation((String) pageObject.get("item")));
                String description = (String) pageObject.get("description");
                RecipePage.RecipeType recipeType = RecipePage.RecipeType.valueOf((String) pageObject.get("recipeType"));
                return new Page[]{new RecipePage(item, description, recipeType)};
            default:
                throw new IllegalArgumentException("Unknown page type \'" + type + "\'");
        }
    }

    @SuppressWarnings("SameParameterValue")
    public JSONObject parseFile(String fileLocation) {
        JSONParser parser = new JSONParser();
        ClassLoader classLoader = this.getClass().getClassLoader();
        try {
            return (JSONObject) parser.parse(new InputStreamReader(classLoader.getResourceAsStream(fileLocation)));
        } catch (IOException | ParseException e) {
            return null;
        }
    }

    public JSONObject[] parseJSONArray(JSONArray jsonArray) {
        JSONObject[] jsonObjects = new JSONObject[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonObjects[i] = (JSONObject) jsonArray.get(i);
        }
        return jsonObjects;
    }

    public Chapter[] parseListPageItems(JSONObject[] pageArray) {
        Chapter[] pages = new Chapter[pageArray.length];
        for (int i = 0; i < pageArray.length; i++) {
            String name = (String) pageArray[i].get("name");
            ItemStack icon = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation((String) pageArray[i].get("icon"))));
            String id = (String) pageArray[i].get("id");
            pages[i] = new Chapter(name, id, icon);
        }
        return pages;
    }

    public Page[] parsePages(JSONObject[] pageArray) {
        Page[] pages = new Page[pageArray.length];
        for (int i = 0; i < pageArray.length; i++) {
            pages[i] = this.parsePages(pageArray[i])[0];
        }
        return pages;
    }

    public Page getMainPage() {
        return mainPage;
    }
}
