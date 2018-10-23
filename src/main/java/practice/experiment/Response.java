package practice.experiment;

import com.alibaba.fastjson.JSON;

public class Response {
    private int samplingRatio;
    private int updatePeriod;

    public int getSamplingRatio() {
        return samplingRatio;
    }

    public void setSamplingRatio(int samplingRatio) {
        this.samplingRatio = samplingRatio;
    }

    public int getUpdatePeriod() {
        return updatePeriod;
    }

    public void setUpdatePeriod(int updatePeriod) {
        this.updatePeriod = updatePeriod;
    }

    public static void main(String[] args) {
        Response res = new Response();
        res.setSamplingRatio(5);
        res.setUpdatePeriod(5 * 60);

        System.out.println(JSON.toJSON(res));
    }
}
