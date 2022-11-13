package api;

import Pojo.Modeles.Root;
import Pojo.Modeles.TestPetData.RequestRoot;
import Pojo.Modeles.TestPetData.ResponseRoot;
import api.Pojos.Pet.DeletePet;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestPet {

    String URL = "https://petstore.swagger.io/v2";
    public static Long useIdPets;
    //asda

    @Test
    @DisplayName("Получение списка питомцев по статусу")
    @Order(1)
    public void GetPets() {

        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpec());

        List<Root> pets = given()
                .when()
                .get("/pet/findByStatus?status=available")
                .then().log().all()
                .extract().body().jsonPath().getList(".", Root.class);

    }
    @Test
    @DisplayName("Создание питомца")
    @Order(2)
    public void postPet() {
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpec());

        String checkName = "FoxyDoggy";

        ArrayList<String> strings = new ArrayList<String>();
        strings.add("string");

        RequestRoot requestRoot = new RequestRoot("FoxyDoggy", strings);
        ResponseRoot responseRoot = given()
                .body(requestRoot)
                .when()
                .post("/pet")
                .then().log().all()
                .extract().as(ResponseRoot.class);

        Assert.assertEquals(checkName, responseRoot.getName());
        Assert.assertEquals(strings, responseRoot.getPhotoUrls());

        Long getIdPets = responseRoot.getId();
        useIdPets = getIdPets;


        System.out.println("ID created pets: "+getIdPets);


    }

    @Test
    @DisplayName("Обновление питомца")
    @Order(3)
    public void updatePet() {
        Specifications.installSpecification(Specifications.requestSpecUpdate(URL),Specifications.responseSpec());
        String newName = "QA Automation";

        ArrayList<String> strings = new ArrayList<String>();
        strings.add("string");

        RequestRoot requestRoot11 = new RequestRoot(useIdPets, newName);

        ResponseRoot responseRoot = given()
                .body(requestRoot11)
                .when()
                .post("/pet")
                .then().log().all()
                .extract().as(ResponseRoot.class);

        Assert.assertEquals(newName, responseRoot.getName());

    }

    @Test
    @DisplayName("Поиск питомца по Id")
    @Order(4)
    public void getPetById() {
        Specifications.installSpecification(Specifications.requestSpecUpdate(URL),Specifications.responseSpec());

        ResponseRoot petsOne = given()
                .pathParam("petId", useIdPets)
                .when()
                .get("/pet/{petId}")
                .then().log().all()
                .extract().as(ResponseRoot.class);

        Assert.assertEquals(useIdPets, petsOne.getId());

    }

    @Test
    @DisplayName("Удаление питомца")
    @Order(5)
    public void delPetById(){
        Specifications.installSpecification(Specifications.requestSpecUpdate(URL),Specifications.responseSpec());

        DeletePet petsOne = given()
                .header("api_key","special-key")
                .pathParam("petId", useIdPets)
                .when()
                .delete("/pet/{petId}")
                .then().log().all()
                .extract().body().as(DeletePet.class);

    }

    @Test
    @DisplayName("Поиск удаленного питомца")
    @Order(6)
    public void getPetByIdLast(){

        Specifications.installSpecification(Specifications.requestSpecUpdate(URL),Specifications.responseSpec404());

        DeletePet deletePetCheck = new DeletePet("Pet not found");


        DeletePet deletePet = given()
                .pathParam("petId", useIdPets)
                .when()
                .get("/pet/{petId}")
                .then().log().all()
                .extract().as(DeletePet.class);

        Assert.assertEquals(deletePetCheck.getMessage(), deletePet.getMessage());


    }

}







