package practice.smartmvc.chp5;

import practice.smartmvc.chp2.ArrayUtil;
import practice.smartmvc.chp2.CodecUtil;
import practice.smartmvc.chp2.StreamUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

public class RequestHelper {
    public static Param createParm(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<FormParam>();
        formParamList.addAll(parseParameterNames(request));
        formParamList.addAll(parseInputStream(request));
        return new Param(formParamList);
    }

    private static Collection<? extends FormParam> parseInputStream(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<>();
        String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
        if (StringUtil.isNotEmpty(body)) {
            String[] kvs = StringUtil.splitString(body, "&");
            for (String kv : kvs) {
                String[] array = StringUtil.splitString(kv, "=");
                if (ArrayUtil.isNotEmpty(array)
                        && array.length == 2) {
                    String fieldName = array[0];
                    String fieldValue = array[1];
                    formParamList.add(new FormParam(fieldName, fieldValue));
                }
            }
        }
        return  formParamList;
    }


    private static Collection<? extends FormParam> parseParameterNames(HttpServletRequest request) {
        List<FormParam> formParamLsit = new ArrayList<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String fieldName = paramNames.nextElement();
            String[] fieldValues = request.getParameterValues(fieldName);
            if (ArrayUtil.isNotEmpty(fieldValues)) {
                Object fieldValue;
                if (fieldValues.length == 1) {
                    fieldValue = fieldValues[0];
                } else {
                    StringBuilder sb = new StringBuilder("");
                    for (int i = 0; i < fieldValues.length; ++i) {
                        sb.append(fieldValues[i]);
                        if (i != fieldValues.length - 1) {
                            sb.append(StringUtil.SEPARATOR);
                        }
                    }
                    fieldValue = sb.toString();
                }
                formParamLsit.add(new FormParam(fieldName, fieldValue));
            }
        }
        return formParamLsit;
    }
}
