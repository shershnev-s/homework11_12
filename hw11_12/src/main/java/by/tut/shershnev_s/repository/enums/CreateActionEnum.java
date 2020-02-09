package by.tut.shershnev_s.repository.enums;

public enum CreateActionEnum {

    CREATE_ROLE_TABLE("CREATE TABLE role(id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, " +
            "name VARCHAR(20) NOT NULL, description VARCHAR(30) NOT NULL);"),

    CREATE_USER_TABLE("CREATE TABLE user(id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, role_id INT, " +
                              "username VARCHAR(20) NOT NULL, password VARCHAR(20) NOT NULL, created_by DATE NOT NULL, " +
                              "foreign key(role_id) REFERENCES role(id) ON DELETE CASCADE);");

    private final String query;

    CreateActionEnum(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
