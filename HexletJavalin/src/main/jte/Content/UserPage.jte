@import dto.org.example.UsersPage
@param UsersPage page

@template.layout.page(
content = @`
    <a href="/users/build">New User</a>
    @for(var user : page.getUsers())
        <div>${user.getName()}</div>
    @endfor
`
)
