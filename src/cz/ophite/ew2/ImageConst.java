package cz.ophite.ew2;

public enum ImageConst
{
    GAME("icon_game.png"),
    NEW_GAME("icon_new_game.png"),
    CLOSE("icon_close.png"),
    GIVE_UP("icon_give_up.png"),
    SHOP("icon_shop.png"),
    BUY("icon_buy.png"),
    SELL("icon_sell.png"),
    SCREENSHOT("icon_screen.png");

    private static final String IMAGE_PATH = "image/";
    private String name;

    private ImageConst(String name)
    {
        this.name = name;
    }

    public String getPath()
    {
        return IMAGE_PATH + name;
    }

    @Override
    public String toString()
    {
        return getPath();
    }
}
