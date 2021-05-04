package com.middleearth.nameclassifier;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClassifierController {

	@GetMapping("/")
	public String index(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "algorithm", required = false, defaultValue = "logistic") String algorithm, Model model)
			throws IOException, Exception {

		model.addAttribute("algorithm", algorithm);

		if (name != null && !name.isEmpty()) {
			model.addAttribute("name", name);

			String modelPath;
			switch (algorithm) {
				case "j48":
					modelPath = "middle-earth.j48.model";
					break;
				case "naive-bayes":
					modelPath = "middle-earth.naive-bayes.model";
					break;
				case "logistic":
				default:
					modelPath = "middle-earth.logistic.model";
					break;
			}

			NameModelClassifier modelClassifier = new NameModelClassifier(modelPath);

			String result = modelClassifier.classify(name);

			if (result != null) {
				model.addAttribute("result", result);
			}

		}

		return "index";
	}

}
