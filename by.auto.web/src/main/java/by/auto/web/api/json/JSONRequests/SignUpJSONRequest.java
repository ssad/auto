package by.auto.web.api.json.JSONRequests;

import by.auto.domain.common.enums.RoleName;
import by.auto.web.validation.constraints.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;

@Company(role = "roleName", company = "company", category = "categoryId")
@Confirm(field = "password", message = "{Confirm.password}")
public class SignUpJSONRequest {
    @Login
    @NotEmpty(message = "{NotEmpty.login}")
    @Size(max = 50, min = 6)
    private String login;

    @Size(max = 50)
    private String company;

    private String categoryId;

    @EmailPhone
    @NotEmpty(message = "{NotEmpty.emailPhone}")
    private String emailPhone;

    @Password(message = "{Password}")
    @Size(max = 500)
    private String password;
    @Size(max = 500)
    private String confirmPassword;

    @Role(message = "{RoleName}")
    private RoleName roleName;

    @AssertTrue(message = "{NotFalse.licenseAgreement}")
    private boolean licenseAgreement;

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(final String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(final RoleName roleName) {
        this.roleName = roleName;
    }

    public boolean isLicenseAgreement() {
        return licenseAgreement;
    }

    public void setLicenseAgreement(final boolean licenseAgreement) {
        this.licenseAgreement = licenseAgreement;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getEmailPhone() {
        return emailPhone;
    }

    public void setEmailPhone(String emailPhone) {
        this.emailPhone = emailPhone;
    }
}