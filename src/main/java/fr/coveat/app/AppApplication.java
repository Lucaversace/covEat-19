package fr.coveat.app;

import fr.coveat.app.model.Dish;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

		SessionFactory factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();

//		Dish dish = new Dish();
//		dish.setName("Produit magueule");
//		dish.setDescription("Produit magueule");
//		dish.setImageUrl("Produit magueule");
//		dish.setPrice(50.5);

//		session.save(dish);
		transaction.commit();

		session.close();
		factory.close();
	}

}
