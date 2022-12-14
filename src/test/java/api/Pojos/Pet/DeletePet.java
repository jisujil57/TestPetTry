package api.Pojos.Pet;

public class DeletePet {
    private Integer code;
    private String type;
    private String message;

    public DeletePet() {}
    public DeletePet(String message) {this.message = message;}

    public DeletePet(Integer code, String type, String message) {
        this.code = code;
        this.type = type;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
