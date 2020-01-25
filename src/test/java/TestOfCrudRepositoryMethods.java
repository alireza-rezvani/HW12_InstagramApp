import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestOfCrudRepositoryMethods {

    @BeforeEach
    public void setup(){
        List<TestEntity> allEntities = TestEntityRepository.getInstance().findAll();
        if (allEntities != null && !allEntities.isEmpty()) {
            for (TestEntity i : allEntities) {
                TestEntityRepository.getInstance().remove(i);
            }
        }
    }

    @Test
    @DisplayName("Find All Test")
    public void findAllTest(){

        //create some entities
        TestEntity entity1 = new TestEntity(null,"Test Entity 1");
        TestEntity entity2 = new TestEntity(null,"Test Entity 2");


        //save entities
        TestEntity savedEntity1 = TestEntityRepository.getInstance().save(entity1);
        TestEntity savedEntity2 = TestEntityRepository.getInstance().save(entity2);

        //find all
        List<TestEntity> allEntities = new ArrayList<>(TestEntityRepository.getInstance().findAll());

        //test
        Assertions.assertTrue(
                allEntities.size() ==2
                        && allEntities.contains(entity1)
                        && allEntities.contains(entity2)
        );

        //display all
        System.out.println("\u2705 Find All Test");
        System.out.println("\n\u29bf All Entities List:");
        TestEntityRepository.getInstance().findAll().forEach(System.out::println);
    }


    @Test
    @DisplayName("Find By Id Test")
    public void findByIdTest(){
        //create some entities
        TestEntity entity1 = new TestEntity(null,"Test Entity 1");
        TestEntity entity2 = new TestEntity(null,"Test Entity 2");
        TestEntity entity3 = new TestEntity(null,"Test Entity 3");


        //save entities
        TestEntity savedEntity1 = TestEntityRepository.getInstance().save(entity1);
        TestEntity savedEntity2 = TestEntityRepository.getInstance().save(entity2);
        TestEntity savedEntity3 = TestEntityRepository.getInstance().save(entity3);

        //test
        Assertions.assertTrue(
                TestEntityRepository.getInstance().findById(savedEntity2.getId()+1).getTitle().equals("Test Entity 3")
        );


        //display founded Entity
        System.out.println("\u2705 Find By Id Test");
        System.out.println("\n\u29bf Founded Entity By Id: " + savedEntity3.getId());
        System.out.println(TestEntityRepository.getInstance().findById(savedEntity3.getId()));

    }


    @Test
    @DisplayName("Save Test")
    public void saveTest(){
        //save an entity
        TestEntity entity1 = TestEntityRepository.getInstance().save(new TestEntity(null,"Test Entity"));

        //save another entity to test
        TestEntity entity2 =  TestEntityRepository.getInstance().save(new TestEntity(null, "Save Test"));

        //test
        Assertions.assertTrue(TestEntityRepository.getInstance().findAll().contains(entity1) && entity2.getId() == entity1.getId()+1);

        //display all saved entities
        System.out.println("\u2705 Save Test");
        System.out.println("\n\u29bf All Entities List:");
        TestEntityRepository.getInstance().findAll().forEach(System.out::println);
    }

    @Test
    @DisplayName("Update Test")
    public void updateTest(){
        //save an entity
        TestEntity savedEntity = TestEntityRepository.getInstance().save(new TestEntity(null,"Test"));

        //display before update
        System.out.println("\u2705 Update Test");
        System.out.println("\n\u29bf All Entities List Before Update:");
        TestEntityRepository.getInstance().findAll().forEach(System.out::println);

        //edit saved entity and Update
        savedEntity.setTitle("Update Test");
        TestEntityRepository.getInstance().update(savedEntity);

        //test
        Assertions.assertTrue(TestEntityRepository.getInstance().findById(savedEntity.getId()).getTitle().equals("Update Test"));

        //display all saved entities
        System.out.println("\u29bf All Entities List After Update:");
        TestEntityRepository.getInstance().findAll().forEach(System.out::println);
    }


    @Test
    @DisplayName("Is Existing Test")
    public void isExistingTest(){
        //save an entity
        TestEntity savedEntity1 = TestEntityRepository.getInstance().save(new TestEntity(null,"Test"));

        //test
        Assertions.assertTrue(
                TestEntityRepository.getInstance().isExisting(savedEntity1.getId())
                        && !TestEntityRepository.getInstance().isExisting(savedEntity1.getId() + 1)
        );

        //display all
        System.out.println("\u2705 Is Existing Test");
        System.out.println("\n\u29bf All Entities List:");
        TestEntityRepository.getInstance().findAll().forEach(System.out::println);
    }

    @Test
    @DisplayName("Remove Test")
    public void removeTest(){
        //save some entities
        TestEntity savedEntity1 = TestEntityRepository.getInstance().save(new TestEntity(null,"Test 1"));
        TestEntity savedEntity2 = TestEntityRepository.getInstance().save(new TestEntity(null,"Test 2"));

        System.out.println("\u2705 Remove Test");
        //display all entities
        System.out.println("\n\u29bf All Entities List Before Remove:");
        TestEntityRepository.getInstance().findAll().forEach(System.out::println);

        //remove first Entity
        TestEntityRepository.getInstance().remove(TestEntityRepository.getInstance().findById(savedEntity1.getId()));

        //display all entities
        System.out.println("\u29bf All Entities List After Remove:");
        TestEntityRepository.getInstance().findAll().forEach(System.out::println);

        //test
        Assertions.assertTrue(!TestEntityRepository.getInstance().isExisting(savedEntity2.getId() - 1));
    }

    @Test
    @DisplayName("Remove By Id Test")
    public void removeByIdTest(){

        //save some entities
        TestEntity savedEntity1 = TestEntityRepository.getInstance().save(new TestEntity(null,"Test 1"));
        TestEntity savedEntity2 = TestEntityRepository.getInstance().save(new TestEntity(null,"Test 2"));

        System.out.println("\u2705 Remove By Id Test");
        //display all entities
        System.out.println("\n\u29bf All Entities List Before Remove:");
        TestEntityRepository.getInstance().findAll().forEach(System.out::println);

        //remove first entity
        TestEntityRepository.getInstance().removeById(savedEntity1.getId());

        //display all entities
        System.out.println("\u29bf All Entities List After Removing Id: " + savedEntity1.getId());
        TestEntityRepository.getInstance().findAll().forEach(System.out::println);

        //test
        Assertions.assertTrue(!TestEntityRepository.getInstance().isExisting(savedEntity1.getId()));
    }



}
