package day_09;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Department;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
import static org.hamcrest.MatcherAssert.*;

import testbase.HR_ORDS_TestBase;
import utility.ConfigurationReader;

import java.util.ArrayList;
import java.util.List;

public class TestingOutLombokDependency extends HR_ORDS_TestBase {

    @Test
    public void testDepartmentPOJO(){

        Department d = new Department();

        d.setDepartment_id(100);
        System.out.println(d.getDepartment_id());

        Department d2 = new Department(110,"ABC",12,1700);
        System.out.println("d2 = " + d2);

    }

    @DisplayName("GET /departments and save List of POJO")
    @Test
    public void testDepartmentJsonArrayTOListOfPOJO(){

        List<Department> allDeps = get("/departments")
                                        .jsonPath().getList("items", Department.class);
        //allDeps.forEach(System.out::println);

        // COPY THE CONTENT OF THIS LIST INTO NEW LIST
        // AND ONLY PRINT IF THE DEP MANAGER ID IS NOT 0
        List<Department> departments = new ArrayList<>(allDeps);
        departments.removeIf( eachDep -> eachDep.getManager_id() == 0);
        departments.forEach(System.out::println);
    }

    @DisplayName("GET /departments and filter the result with JsonPath groovy")
    @Test
    public void testFilterResultWithGroovy(){

        JsonPath jp = get("/departments").jsonPath();
        List<Department> allDeps = jp.getList("items.findAll { it.manager_id != null }", Department.class);
        //allDeps.forEach(System.out::println);

        // what if I just wanted to get List<String> to store DepartmentName
        List<String> depNames = jp.getList("items.department_name") ;
        System.out.println("depNames = " + depNames);
        // -->> items.department_name (all)
        // -->> items.findAll {it.manager_id>0 }.department_name (filtered for manager_id more than 0)
        List<String> depNamesFiltered = jp.getList("items.findAll {it.manager_id>0 }.department_name") ;
        System.out.println("depNamesFiltered = " + depNamesFiltered);

        List<Integer> allDepsId = jp.getList("items.department_id");
        System.out.println("allDepsId = " + allDepsId);
// Get all departments ID if its more than 70
        List<Integer> allDepsIdFiltered = jp.getList("items.department_id.findAll { it > 70 }");
        System.out.println("allDepsIdFiltered = " + allDepsIdFiltered);

// what if we have more than one condition for example : department_id between 70 - 100
        List<Integer> deps70to100 =
                jp.getList("items.department_id.findAll{ it >= 70 && it <= 100  }") ;
        System.out.println("deps70to100 = " + deps70to100);

// get the name of departments if department_id between 70 - 100
        List<String> allDepNamesFiltered = jp.getList("items.findAll {it.department_id >= 70 && it.department_id <= 100}.department_name");
        System.out.println("allDepNamesFiltered = " + allDepNamesFiltered);
    }
}
