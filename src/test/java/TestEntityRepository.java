import ir.maktab32.java.homeworks.instagram.config.hibernate.repositories.CrudRepository;
import org.hibernate.Session;

public class TestEntityRepository extends CrudRepository<TestEntity, Long> {
    @Override
    protected Class<TestEntity> getEntityClass() {
        return TestEntity.class;
    }

    @Override
    protected Session getSession() {
        return HibernateUtilForTest.getSessionH2();
    }

    private static TestEntityRepository testEntityRepository;
    public static TestEntityRepository getInstance(){
        if (testEntityRepository == null)
            testEntityRepository = new TestEntityRepository();
        return testEntityRepository;
    }
}
