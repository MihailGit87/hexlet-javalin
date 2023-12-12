package org.example.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.dto.BasePage;

@AllArgsConstructor
@Getter
public class UserPage {
    private User user;
    private BasePage page;

    public UserPage(User user) {
        this.user = user;
    }
}
