package core.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				// 建立 Registry
				registry = new StandardServiceRegistryBuilder().configure().build();

				// 建立 MetadataSources
				MetadataSources metadataSource = new MetadataSources(registry);

				// 建立 Metadata
				Metadata metadata = metadataSource.getMetadataBuilder().build();

				// 建立 SessionFactory
				sessionFactory = metadata.getSessionFactoryBuilder().build();

			} catch (Exception e) {
				e.printStackTrace();
				// 如果初始化失敗，通常會在這裡把 registry 銷毀，避免資源卡住
				if (registry != null) {
					StandardServiceRegistryBuilder.destroy(registry);
				}
			}
		}
		return sessionFactory;
	}

	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
}

