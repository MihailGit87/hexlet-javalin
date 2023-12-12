package org.example.routes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MainPage {
    private Boolean visited;
    private String currentUser;

    public MainPage(String currentUser) {
        this.currentUser = currentUser;
    }

    public Boolean isVisited() {
        return visited;
    }
}