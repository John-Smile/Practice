package practice.mh.chp4;

import java.io.File;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CachingItemSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class ItemBasedRecommendTest {
	public static void main(String[] args) throws Exception {
		DataModel model = new FileDataModel(new File(".\\src\\main\\resources\\data\\ua.base"));
		ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
		CachingItemSimilarity i = null;
		Recommender recommender = new GenericItemBasedRecommender(model, similarity);
        System.out.println(recommender.recommend(660, 50));	
	}
}
