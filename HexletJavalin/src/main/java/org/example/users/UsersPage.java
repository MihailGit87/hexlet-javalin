package org.example.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.dto.BasePage;

import java.util.List;

@AllArgsConstructor
@Getter
public class UsersPage {
    private List<User> users;
    private String header;
    private BasePage page;

    public UsersPage(List<User> users) {
        this.users = users;
    }
}
