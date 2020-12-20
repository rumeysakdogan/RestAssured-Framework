package day_09;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Department;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import testbase.HR_ORDS_TestBase;
import utility.ConfigurationReader;

import java.util.ArrayList;
import java.util.List;

public class AdvancedJsonPathWithGroovy extends HR_ORDS_TestBase {

    @Test
    public void testDepartmentPOJO() {

        Department d = new Department();

        d.setDepartment_id(100);
        System.out.println(d.getDepartment_id());

        Department d2 = new Department(110, "ABC", 12, 1700);
        System.out.println("d2 = " + d2);

    }

    @DisplayName("GET /departments and save List of POJO")
    @Test
    public void testDepartmentJsonArrayTOListOfPOJO() {

        List<Department> allDeps = get("/departments")
                .jsonPath().getList("items", Department.class);
        //allDeps.forEach(System.out::println);

        // COPY THE CONTENT OF THIS LIST INTO NEW LIST
        // AND ONLY PRINT IF THE DEP MANAGER ID IS NOT 0
        List<Department> departments = new ArrayList<>(allDeps);
        departments.removeIf(eachDep -> eachDep.getManager_id() == 0);
        departments.forEach(System.out::println);
    }

    @DisplayName("GET /departments and filter the result with JsonPath groovy")
    @Test
    public void testFilterResultWithGroovy() {

        JsonPath jp = get("/departments").jsonPath();
        List<Department> allDeps = jp.getList("items.findAll { it.manager_id != null }", Department.class);
        //allDeps.forEach(System.out::println);

        // what if I just wanted to get List<String> to store DepartmentName
        List<String> depNames = jp.getList("items.department_name");
        System.out.println("depNames = " + depNames);
        // -->> items.department_name (all)
        // -->> items.findAll {it.manager_id>0 }.department_name (filtered for manager_id more than 0)
        List<String> depNamesFiltered = jp.getList("items.findAll {it.manager_id>0 }.department_name");
        System.out.println("depNamesFiltered = " + depNamesFiltered);

        List<Integer> allDepsId = jp.getList("items.department_id");
        System.out.println("allDepsId = " + allDepsId);
// Get all departments ID if its more than 70
        List<Integer> allDepsIdFiltered = jp.getList("items.department_id.findAll { it > 70 }");
        System.out.println("allDepsIdFiltered = " + allDepsIdFiltered);

// what if we have more than one condition for example : department_id between 70 - 100
        List<Integer> deps70to100 =
                jp.getList("items.department_id.findAll{ it >= 70 && it <= 100  }");
        System.out.println("deps70to100 = " + deps70to100);

// get the name of departments if department_id between 70 - 100
        List<String> allDepNamesFiltered = jp.getList("items.findAll {it.department_id >= 70 && it.department_id <= 100}.department_name");
        System.out.println("allDepNamesFiltered = " + allDepNamesFiltered);

        // findAll-->> will return all matching result
        // find-->> will return first match for the condition
        String depNameWithId10 = jp.getString("items.find {it.department_id = 10}.department_name");
        System.out.println("depNameWithId10 = " + depNameWithId10);

        //find – finds the first item matching a closure predicate
        //collect – collect the return value of calling a closure on each item in a collection
        //sum – Sum all the items in the collection
        //max/min – returns the max/min values of the collection

        // sum / min / max  collect
        // get the sum of entire department_id
        int sumOfAllDeptIds = jp.getInt("items.department_id.sum()");
        int sumOfAllDepIDs2 = jp.getInt("items.department_id.findAll{it}.sum()");

        System.out.println("sumOfAllDeptIds = " + sumOfAllDeptIds);
        System.out.println("sumOfAllDepIDs2 = " + sumOfAllDepIDs2);

        // get the lowest department_id
        int lowestDepId = jp.getInt("items.department_id.min()") ;
        System.out.println("lowestDepId = " + lowestDepId);

        // get the highest department_id
        int highestDepId = jp.getInt("items.department_id.max()") ;
        System.out.println("highestDepId = " + highestDepId);

        // print number 5 dep ID
        System.out.println("number 5 dep ID" + jp.getInt("items.department_id[4]")   );
        // print number last dep ID
        System.out.println("last dep ID " + jp.getInt("items.department_id[-1]")   );
        // print from index 7 till index 10 dep ID
        System.out.println("index 7-10 dep ID " + jp.getList("items.department_id[7..10]")   );


        String shortestDepName = jp.getString("items.department_name.min{ it.length() }") ;
        System.out.println("shortestDepName = \n\t" + shortestDepName);

        String longestDepName = jp.getString("items.department_name.max{ it.length() }") ;
        System.out.println("longestDepName = \n\t" + longestDepName);

        String shortestDepNameID =
                jp.getString("items.min{ it.department_name.length() }.department_id") ;
        System.out.println("shortestDepNameID = \n\t" + shortestDepNameID);

        // print number 5 dep ID
        System.out.println("number 5 dep ID" + jp.getInt("items.department_id[4]")   );
        // print number last dep ID
        System.out.println("last dep ID " + jp.getInt("items.department_id[-1]")   );
        // print from index 7 till index 10 dep ID
        System.out.println("index 7-10 dep ID " + jp.getList("items.department_id[7..10]")   );

        System.out.println("collect method demo \n");
        // get all the department name uppercase
        List<String> upperCaseDepNames
                = jp.getList("items.department_name.collect{ it.toUpperCase() }") ;
        upperCaseDepNames.forEach(System.out::println);

        System.out.println(" \ncollect method demo 2\n");
        List<String> idDepNames
                = jp.getList("items.collect{'Deps ' + it.department_id + ', ' + it.department_name }") ;
        idDepNames.forEach(System.out::println);
    }
}
