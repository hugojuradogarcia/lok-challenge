package com.lok.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;

    @NotBlank(message="Please provide a name.")
    @Size(max = 50, message = "The name must contain less than 50 characters.")
    private String name;

    @Size(max = 50, message = "The surnames must contain less than 50 characters.")
    private String surnames;

    @Pattern(regexp="^(([ÑA-Z|ña-z|&]{3}|[A-Z|a-z]{4})\\d{2}((0[1-9]|1[012])(0[1-9]|1\\d|2[0-8])|(0[13456789]|1[012])(29|30)|(0[13578]|1[02])31)(\\w{2})([A|a|0-9]{1}))$|^(([ÑA-Z|ña-z|&]{3}|[A-Z|a-z]{4})([02468][048]|[13579][26])0229)(\\w{2})([A|a|0-9]{1})$",message="Please provide a valid RFC.")
    private String rfc;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message="Please provide a date with the format yyyy-MM-dd.")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date_birth;

    @NotBlank(message="Please provide a username.")
    @Size(max = 50, message = "The username must contain less than 50 characters.")
    private String username;

    @NotBlank(message="Please provide a password.")
    @Size(max = 50, message = "The password must contain less than 50 characters.")
    private String password;

    private List<Address> addresses;
}
