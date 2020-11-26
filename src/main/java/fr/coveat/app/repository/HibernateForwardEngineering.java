package fr.coveat.app.repository;

import fr.coveat.app.model.Dish;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateForwardEngineering {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

        SessionFactory factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Dish dish = new Dish();
        dish.setName("Produit magueule");
//        dish.setDesc("Produit magueule");
//        dish.setImage_url("Produit magueule");
//        dish.setPrice(50);

        session.save(dish);
        transaction.commit();

        session.close();
        factory.close();


    }
}
