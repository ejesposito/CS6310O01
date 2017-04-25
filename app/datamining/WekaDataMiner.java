package datamining;

import scala.collection.immutable.List;
import weka.associations.Apriori;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.core.expressionlanguage.weka.InstancesHelper;
import weka.experiment.InstanceQuery;

/**
 * Created by awolfe on 4/24/17.
 */
public class WekaDataMiner {


    public static String buildAssociate() throws Exception {
        InstanceQuery query = new InstanceQuery();
        query.setUsername("root");
        query.setPassword("root");
        query.setQuery("select * from courses_sessions;");
        // You can declare that your data set is sparse
        // query.setSparseData(true);
        Instances data = query.retrieveInstances();
        data.setClassIndex(data.numAttributes() - 1);

        // build associator
        Apriori apriori = new Apriori();
        apriori.setClassIndex(data.classIndex());
        apriori.buildAssociations(data);
        return String.valueOf(apriori);
    }
}
