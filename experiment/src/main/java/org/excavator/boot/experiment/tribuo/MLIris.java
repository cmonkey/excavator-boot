package org.excavator.boot.experiment.tribuo;

import lombok.SneakyThrows;
import org.tribuo.DataSource;
import org.tribuo.Model;
import org.tribuo.MutableDataset;
import org.tribuo.classification.Label;
import org.tribuo.classification.LabelFactory;
import org.tribuo.classification.dtree.CARTClassificationTrainer;
import org.tribuo.classification.evaluation.LabelEvaluator;
import org.tribuo.classification.sgd.linear.LogisticRegressionTrainer;
import org.tribuo.data.csv.CSVLoader;
import org.tribuo.evaluation.TrainTestSplitter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MLIris {
    @SneakyThrows
    private static Path getCurrentPath(String fileName) {
        var classLoader = MLIris.class.getClassLoader();
        var uri = classLoader.getResource(fileName).toURI();
        var path = Paths.get(uri);
        return path;
    }
    @SneakyThrows
    public static void main(String[] args) {
        // Load labelled iris data
        var irisHeaders = new String[]{"sepalLength", "sepalWidth", "petalLength", "petalWidth", "species"};
        DataSource<Label> irisesSource =
                new CSVLoader<>(new LabelFactory()).loadDataSource(
                        getCurrentPath("bezdekIris.data"),
                        /* Output column   */ irisHeaders[4],
                        /* Column headers  */ irisHeaders);

        // Split iris data into training set (70%) and test set (30%)
        var splitIrisData = new TrainTestSplitter<>(irisesSource,
                /* Train fraction */ 0.7,
                /* RNG seed */ 1L);
        var trainData = new MutableDataset<>(splitIrisData.getTrain());
        var testData = new MutableDataset<>(splitIrisData.getTest());

        // We can train a decision tree
        var cartTrainer = new CARTClassificationTrainer();
        Model<Label> tree = cartTrainer.train(trainData);
        // Or a logistic regression
        var linearTrainer = new LogisticRegressionTrainer();
        Model<Label> linear = linearTrainer.train(trainData);

        // Finally we make predictions on unseen data
        // Each prediction is a map from the output names (i.e. the labels) to the scores/probabilities
        //Prediction<Label> prediction = linear.predict(testData.get(0));

        // Or we can evaluate the full test dataset, calculating the accuracy, F1 etc.
        var evaluation = new LabelEvaluator().evaluate(linear,testData);
        // we can inspect the evaluation manually
        double acc = evaluation.accuracy();
        System.out.printf("acc = %f", acc);
        // which returns 0.978
        // or print a formatted evaluation string
        System.out.println(evaluation.toString());
    }
}
