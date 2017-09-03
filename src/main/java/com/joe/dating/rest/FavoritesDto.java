package com.joe.dating.rest;

import java.util.ArrayList;
import java.util.List;

public class FavoritesDto {
    private List<ProfileEvent> myFavorites = new ArrayList<>();
    private List<ProfileEvent> favoritedMe = new ArrayList<>();

    public List<ProfileEvent> getMyFavorites() {
        return myFavorites;
    }

    public void setMyFavorites(List<ProfileEvent> myFavorites) {
        this.myFavorites = myFavorites;
    }

    public List<ProfileEvent> getFavoritedMe() {
        return favoritedMe;
    }

    public void setFavoritedMe(List<ProfileEvent> favoritedMe) {
        this.favoritedMe = favoritedMe;
    }
}
