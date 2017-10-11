package cz.fi.muni.pa165.tasks;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;

 
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task02 extends AbstractTestNGSpringContextTests {

	@PersistenceUnit
	private EntityManagerFactory emf;
        
        private Category kitchen;
        private Category electro;
        private Product  flashlight;
        private Product  kitchenRobot;
        private Product  plate;

        @BeforeClass
        private void setUp(){
            EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Category kitchen = new Category();
		kitchen.setName("kitchen");
		em.persist(kitchen);
                
                Category electro = new Category();
		electro.setName("electro");
		em.persist(electro);
                em.persist(kitchen);
                
                Product flashlight = new Product();
                flashlight.setName("flashlight");
                flashlight.addCategory(electro);
                
                Product kitchenRobot = new Product();
                kitchenRobot.setName("kitchenRobot");
                kitchenRobot.addCategory(electro);
                kitchenRobot.addCategory(kitchen);

                Product plate = new Product();
                plate.setName("plate");
                plate.addCategory(kitchen);
                
                em.persist(flashlight);
                em.persist(kitchenRobot);
                em.persist(plate);
                
		em.getTransaction().commit();
		em.close();
	
        }
        
	
	private void assertContainsCategoryWithName(Set<Category> categories,
			String expectedCategoryName) {
		for(Category cat: categories){
			if (cat.getName().equals(expectedCategoryName))
				return;
		}
			
		Assert.fail("Couldn't find category "+ expectedCategoryName+ " in collection "+categories);
	}
	private void assertContainsProductWithName(Set<Product> products,
			String expectedProductName) {
		
		for(Product prod: products){
			if (prod.getName().equals(expectedProductName))
				return;
		}
			
		Assert.fail("Couldn't find product "+ expectedProductName+ " in collection "+products);
	}
        
        @Test
        public void testKitchen (){
            EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
                Category cat = em.find(Category.class, kitchen.getId());
                assertContainsProductWithName(cat.getProducts(), "plate");
                assertContainsProductWithName(cat.getProducts(), "kitchenRobot");
                em.close();
        }
        
        @Test
        public void testElectro (){
            EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
                Category cat = em.find(Category.class, electro.getId());
                assertContainsProductWithName(cat.getProducts(), "flashlight");
                assertContainsProductWithName(cat.getProducts(), "kitchenRobot");
                em.close();
        }
        
        @Test
        public void testFlashlight (){
            EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
                Product prod = em.find(Product.class, flashlight.getId());
                assertContainsCategoryWithName(prod.getCategories(), "electro");
                em.close();
        }
        
        @Test
        public void testKitchenRobot (){
            EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
                Product prod = em.find(Product.class, kitchenRobot.getId());
		assertContainsCategoryWithName(prod.getCategories(), "kitchen");
                assertContainsCategoryWithName(prod.getCategories(), "electro");
		em.close();
        }
        
        @Test
        public void testPlate (){
            EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
                Product prod = em.find(Product.class, plate.getId());
                assertContainsCategoryWithName(prod.getCategories(), "kitchen");
		em.close();
        }

	
}
