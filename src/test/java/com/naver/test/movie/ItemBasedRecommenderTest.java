package com.naver.test.movie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveArrayIterator;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class ItemBasedRecommenderTest {

	@Before
	public void setUp() throws Exception {
		// userID, itemID, value(rating)
		ClassPathResource resource = new ClassPathResource("u.data");
		BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
		BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/movies.csv"));

		String line;
		while ((line = br.readLine()) != null) {
			String[] values = line.split("\\t", -1);
			bw.write(values[0] + "," + values[1] + "," + values[2] + "\n");
		}
		br.close();
		bw.close();
	}

	@Test
	public void test() throws IOException, TasteException {
		ClassPathResource resource = new ClassPathResource("movies.csv");

		DataModel dataModel = new FileDataModel(resource.getFile());

		ItemSimilarity similarity = new LogLikelihoodSimilarity(dataModel);

		GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, similarity);

		printRecommendResult(dataModel, similarity, recommender);
	}

	@Test
	public void genericDataModeltest() throws IOException, TasteException {
		FastByIDMap<Collection<Preference>> data = new FastByIDMap<Collection<Preference>>();

		ClassPathResource resource = new ClassPathResource("u.data");
		BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
		String line;
		while ((line = br.readLine()) != null) {
			String[] values = line.split("\\t", -1);
			Long userId = Long.parseLong(values[0]);
			Long itemId = Long.parseLong(values[1]);
			Float value = Float.parseFloat(values[2]);

			if (data.get(userId) == null) {
				List<Preference> row = new ArrayList<Preference>();
				row.add(new GenericPreference(userId, itemId, value));
				data.put(userId, row);
			} else {
				List<Preference> list = (List<Preference>) data.get(userId);
				list.add(new GenericPreference(Long.parseLong(values[0]), itemId, value));
			}
		}
		br.close();

		DataModel dataModel = new GenericDataModel(GenericDataModel.toDataMap(data, true));

		ItemSimilarity similarity = new LogLikelihoodSimilarity(dataModel);

		GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, similarity);

		printRecommendResult(dataModel, similarity, recommender);
	}

	private void printRecommendResult(DataModel dataModel, ItemSimilarity similarity,
			GenericItemBasedRecommender recommender) throws TasteException {
		for (LongPrimitiveArrayIterator items = (LongPrimitiveArrayIterator) dataModel.getItemIDs(); items.hasNext();) {
			long itemId = items.nextLong();
			// 10이하의 itemId만 출력
			if (itemId < 10L) {
				List<RecommendedItem> recommendations = recommender.mostSimilarItems(itemId, 5);

				for (RecommendedItem recommendedItem : recommendations) {
					System.out.println(itemId + "," + recommendedItem.getItemID() + "," + recommendedItem.getValue());
				}
			}
		}
	}
}
