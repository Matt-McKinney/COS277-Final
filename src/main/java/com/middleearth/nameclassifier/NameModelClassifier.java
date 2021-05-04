package com.middleearth.nameclassifier;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import weka.classifiers.Classifier;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.SerializationHelper;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class NameModelClassifier {
  private Classifier classifier;

  public NameModelClassifier(String modelPath) throws IOException, Exception {
    Resource resource = new ClassPathResource(modelPath);
    classifier = (FilteredClassifier) SerializationHelper.read(resource.getInputStream());
  }

  public NameModelClassifier(Classifier c) {
    classifier = c;
  }

  public String classify(String inputName) throws Exception {
    ArrayList<String> classVal = new ArrayList<String>();
    classVal.add("Dwarf");
    classVal.add("Elf");
    classVal.add("Hobbit");
    classVal.add("Human");
    classVal.add("Maiar");

    Attribute nameAttribute = new Attribute("name", (ArrayList<String>) null);
    Attribute classAttribute = new Attribute("class", classVal);

    ArrayList<Attribute> attributes = new ArrayList<Attribute>();

    attributes.add(nameAttribute);
    attributes.add(classAttribute);

    Instances data = new Instances("UserSuppliedInstances", attributes, 1);
    data.setClass(classAttribute);

    var inputData = new DenseInstance(2);

    inputData.setDataset(data);
    inputData.setValue(nameAttribute, inputName);

    data.add(inputData);

    return classVal.get((int) classifier.classifyInstance(data.firstInstance()));
  }
}
