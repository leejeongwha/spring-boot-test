package com.naver.test.movie;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class UserBasedRecommenderTest {
	private File file;

	@Before
	public void setUp() throws Exception {
		// userID, itemID, value(rating)
		ClassPathResource resource = new ClassPathResource("dataset.csv");
		file = resource.getFile();
	}

	/**
	 * https://mahout.apache.org/users/recommender/userbased-5-minutes.html
	 * 
	 * user based recommender
	 * 
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		DataModel model = new FileDataModel(file);

		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);

		UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

		// userID 2
		List<RecommendedItem> recommendations = recommender.recommend(2, 3);
		for (RecommendedItem recommendation : recommendations) {
			System.out.println(recommendation);
		}

	}

}
