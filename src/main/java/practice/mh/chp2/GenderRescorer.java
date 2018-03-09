package practice.mh.chp2;

import java.io.File;

import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.IDRescorer;

public class GenderRescorer implements IDRescorer {

	public GenderRescorer(FastIDSet men, FastIDSet women, long userID,
			FastIDSet usersRateMoreMen, FastIDSet usersRateLessMen,
			long userID2, DataModel model) {
		// TODO Auto-generated constructor stub
	}

	public static FastIDSet[] parseMenWomen(File readResourceToTempFile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double rescore(long id, double originalScore) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isFiltered(long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
