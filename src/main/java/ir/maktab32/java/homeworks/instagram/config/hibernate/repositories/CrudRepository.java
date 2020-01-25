package ir.maktab32.java.homeworks.instagram.config.hibernate.repositories;

import ir.maktab32.java.homeworks.instagram.config.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;

public abstract class CrudRepository<Entity, ID extends Serializable> {

    protected abstract Class<Entity> getEntityClass();

    protected abstract Session getSession();

    public Entity save(Entity entity){
        getSession().beginTransaction();
        getSession().save(entity);

        getSession().getTransaction().commit();
        return entity;
    }

    public Entity update(Entity entity){
        getSession().beginTransaction();
        getSession().update(entity);

        getSession().getTransaction().commit();

        return entity;
    }

    public void remove(Entity entity){
        getSession().beginTransaction();
        getSession().remove(entity);

        getSession().getTransaction().commit();

    }

    public void removeById(ID id){
        Entity entity = findById(id);
        if (entity != null) {
            getSession().beginTransaction();
            getSession().remove(entity);

            getSession().getTransaction().commit();

        }
    }

    public List<Entity> findAll(){
        getSession().beginTransaction();
        Query query = getSession().createQuery("from " + getEntityClass().getName(), getEntityClass());
        List<Entity> entities = query.list();

        getSession().getTransaction().commit();

        return entities;
    }

    public Entity findById(ID id){
        getSession().beginTransaction();
        Entity entity = getSession().load(getEntityClass(), id);

        getSession().getTransaction().commit();

        return entity;
    }

    public boolean isExisting(ID id){
        try {
            findById(id).equals(null);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
