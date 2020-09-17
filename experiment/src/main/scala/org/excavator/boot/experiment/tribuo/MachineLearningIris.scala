package org.excavator.boot.experiment.tribuo

import java.nio.file.Paths

import org.tribuo.classification.dtree.CARTClassificationTrainer
import org.tribuo.classification.evaluation.{LabelEvaluation, LabelEvaluator}
import org.tribuo.classification.sgd.linear.LogisticRegressionTrainer
import org.tribuo.{DataSource, Model, MutableDataset, Prediction}
import org.tribuo.classification.{Label, LabelFactory}
import org.tribuo.data.csv.CSVLoader
import org.tribuo.datasource.ListDataSource
import org.tribuo.evaluation.{Evaluation, TrainTestSplitter}

class MachineLearningIris {

  // step 1, load a dataset for classifying the species of irises from a csv
  // step2. split that dataset into training and testing datasets
  // step3. train two types models using different trainers
  // step4. use a model to make predictions on the test set, and devaluat is's performance on the whole test set

  def mlIris(): Unit = {
    val irisHeaders = Array("sepalLength", "sepalWidth", "petaLength", "petalWidth", "species")
    try {
      val irisesSource: DataSource[Label] = new CSVLoader(new LabelFactory()).loadDataSource(
        Paths.get("bezdekIris.data"),
        /* output column */irisHeaders(4),
      /* column headers */irisHeaders:_*)[ListDataSource[Label]]
      ))
      // Split iris data into training set (70%) and test set (30%)
      val splitIrisData = new TrainTestSplitter[](irisesSource,
        /* train fraction */ 0.7,
        /* rng seed */ 1L)
      val trainData = new MutableDataset(splitIrisData.getTrain())[DataSource[Label]]
      val testData = new MutableDataset(splitIrisData.getTest())[DataSource[Label]]

      // we can train a decision tree
       val cartTrainer = new CARTClassificationTrainer()
       val tree:Model[Label] = cartTrainer.train(trainData)

       // or a logistic regression
       val linearTrainer = new LogisticRegressionTrainer()
       val linear:Model[Label] = linearTrainer.train(trainData)

       // Finally we make predictions on unseen data
       // Each prediction is  a map from the output names (i, e the labels) to the scores/probabilities
       val prediction: Prediction[Label] = linear.predict(testData.get(0))

       // or we can evaluate the full test dataset, calculating the accuracy, F1 etc
       val evaluation:Evaluation[Label] = new LabelEvaluation().evaluate(linear, testData)
      import org.tribuo.classification.evaluation.LabelEvaluation
       //We can inspect the evaluation manually
       val acc:Double = evaluation.accuracy()
       
       println(evaluation.toString)
       println(acc)
    }catch {
      case ex:Throwable => {

      }
    }
  }

}
