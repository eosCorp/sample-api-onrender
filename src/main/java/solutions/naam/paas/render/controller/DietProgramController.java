package solutions.naam.paas.render.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/program")
public class DietProgramController {

    @PostMapping
    public Map<String, BigDecimal> computeBMI(@RequestParam("age") int age,
                                              @RequestParam("current_weight") int current_weight,
                                              @RequestParam("height") int height,
                                              @RequestParam("weight_to_loss") int weight_to_loss){

        long idealBMI = 24;
        // Computation of BMI and ideal weight
        long currentBMI = Math.round(current_weight/Math.pow((double) height /100, 2));
        long dream_weight = Math.subtractExact(current_weight, weight_to_loss);
        double ideal_weight = (Math.pow((double) height /100, 2)) * idealBMI ;
        BigDecimal over_weight = BigDecimal.valueOf(current_weight).subtract(BigDecimal.valueOf(ideal_weight)).round(MathContext.DECIMAL32);
        Map<String, BigDecimal> res = new HashMap<>();
        //res.put("Current BMI", new BigDecimal(currentBMI).setScale(1, RoundingMode.HALF_UP));
        //res.put("Dream Weight", new BigDecimal(dream_weight).setScale(2, RoundingMode.HALF_UP));
        //res.put("Ideal Weight", new BigDecimal(ideal_weight).setScale(1, RoundingMode.HALF_UP));
        //res.put("Over Weight", over_weight.setScale(1, RoundingMode.HALF_UP));

        // Computation of CTJE
        double J6 = (((10 * current_weight) + (6.25 * height)) - (5 * age) + 5) * 1.3;
        //res.put("J6", new BigDecimal(J6));
        double K6 = (((10 * current_weight) + (6.25 * height)) - (5 * age) - 161) * 1.3;
        //res.put("K6", new BigDecimal(K6).setScale(3, RoundingMode.HALF_UP));

        double J10 = (((10 * ideal_weight) + (6.25 * height)) - (5 * age) + 5) * 1.3;
        //res.put("J10", new BigDecimal(J10).setScale(3, RoundingMode.HALF_UP));

        double K10 = (((10 * ideal_weight) + (6.25 * height)) - (5 * age) - 161) * 1.3;
        //res.put("K10", new BigDecimal(K10).setScale(3, RoundingMode.HALF_UP));

        double J13 = J6 - J10;
        //res.put("J13", new BigDecimal(J13).setScale(2, RoundingMode.HALF_UP));

        double K13 = K6 - K10;
        //res.put("K13", new BigDecimal(J13).setScale(2, RoundingMode.HALF_UP));

        double J17 = J13/J6;
        //res.put("J17", new BigDecimal(J17).setScale(2, RoundingMode.HALF_UP));

        double K17 = K13/K6;
        //res.put("K17", new BigDecimal(K17).setScale(2, RoundingMode.HALF_UP));


        //DecimalFormat df = new DecimalFormat("#%");
        //System.out.println("J17 " + df.format(J17));
        //System.out.println("K17 " + df.format(K17));

        // Activité importante
        double M6 = ((10 * current_weight) + (6.25 * height) - (5 * age) + 5) * 1.5;
        //res.put("M6", new BigDecimal(M6).setScale(3, RoundingMode.HALF_UP));

        double N6 = ((10 * current_weight) + (6.25 * height) - (5 * age) - 161) * 1.5;
        //res.put("N6", new BigDecimal(N6).setScale(3, RoundingMode.HALF_UP));

        double M10 = ((10 * ideal_weight) + (6.25 * height) - (5 * age) + 5) * 1.5;
        //res.put("M10", new BigDecimal(M10).setScale(3, RoundingMode.HALF_UP));

        double N10 = ((10 * ideal_weight) + (6.25 * height) - (5 * age) - 161) * 1.5;
        //res.put("N10", new BigDecimal(N10).setScale(3, RoundingMode.HALF_UP));

        double M13 = M6 - M10;
        //res.put("M13", new BigDecimal(M13).setScale(3, RoundingMode.HALF_UP));

        double N13 = N6 - N10;
        //res.put("N13", new BigDecimal(N13).setScale(3, RoundingMode.HALF_UP));

        double M17 = M13/M6;
        //res.put("M17", new BigDecimal(M17).setScale(2, RoundingMode.HALF_UP));

        double N17 = N13/N6;
        //res.put("N17", new BigDecimal(N17).setScale(2, RoundingMode.HALF_UP));

        // Ideal 30
        double C15 = J6 - (0.3 * J6);
        //res.put("C15", new BigDecimal(C15).setScale(4, RoundingMode.HALF_UP));

        double D15 = K6 - (0.3 * K6);
        //res.put("D15", new BigDecimal(D15).setScale(4, RoundingMode.HALF_UP));

        double F15 = M6 - (0.3 * M6);
        //res.put("F15", new BigDecimal(F15).setScale(4, RoundingMode.HALF_UP));

        double G15 = N6 - (0.3 * N6);
        //res.put("G15", new BigDecimal(G15).setScale(4, RoundingMode.HALF_UP));

        // Ideal Start
        double C16 = C15 + (J17 * C15);
        //res.put("C16", new BigDecimal(C16).setScale(4, RoundingMode.HALF_UP));

        double D16 = D15 + (K17 * D15);
        //res.put("D16", new BigDecimal(D16).setScale(4, RoundingMode.HALF_UP));

        double F16 = F15 + (M17 * F15);
        //res.put("F16", new BigDecimal(F16).setScale(4, RoundingMode.HALF_UP));

        double G16 = G15 + (N17 * G15);
        //res.put("G16", new BigDecimal(G16).setScale(4, RoundingMode.HALF_UP));

        // Break fast
        double bf_male = 0.15 * C16;
        //res.put("bf_male", new BigDecimal(bf_male).setScale(6, RoundingMode.HALF_UP));
        double bf_female = 0.15 * D16;
        //res.put("bf_female", new BigDecimal(bf_female).setScale(6, RoundingMode.HALF_UP));
        // Snacks
        double snacks_male = (7.5/100) * C16;
        //res.put("snacks_male", new BigDecimal(snacks_male).setScale(7, RoundingMode.HALF_UP));
        double snacks_female = (7.5/100) * D16;
        //res.put("snacks_female", new BigDecimal(snacks_female).setScale(7, RoundingMode.HALF_UP));
        // Lunch
        BigDecimal lunch_male = new BigDecimal(0.45 * C16);
        //res.put("lunch_male", lunch_male.setScale(7, RoundingMode.HALF_UP));
        BigDecimal lunch_female = new BigDecimal(0.45 * D16);
        //res.put("lunch_female", lunch_female.setScale(7, RoundingMode.HALF_UP));
        // Nibbles
        double nibbles_male = (7.5/100) * C16;
        //res.put("nibbles_male", new BigDecimal(nibbles_male).setScale(7, RoundingMode.HALF_UP));
        double nibbles_female = (7.5/100) * D16;
        //res.put("nibbles_female", new BigDecimal(nibbles_female).setScale(7, RoundingMode.HALF_UP));
        // Dinner
        BigDecimal dinner_male = new BigDecimal(0.25 * C16);
        //res.put("dinner_male", dinner_male.setScale(7, RoundingMode.HALF_UP));
        BigDecimal dinner_female = new BigDecimal(0.25 * D16);
        //res.put("dinner_female", dinner_female.setScale(7, RoundingMode.HALF_UP));
        // Totals
        BigDecimal male_daily_total = BigDecimal.valueOf(bf_male).add(BigDecimal.valueOf(snacks_male).add(lunch_male).add(BigDecimal.valueOf(nibbles_male)).add(dinner_male));
        //res.put("male_daily_total", male_daily_total.setScale(7, RoundingMode.HALF_UP));
        BigDecimal female_daily_total = BigDecimal.valueOf(bf_female).add(BigDecimal.valueOf(snacks_female).add(lunch_female).add(BigDecimal.valueOf(nibbles_female)).add(dinner_female));
        //res.put("female_daily_total", female_daily_total.setScale(7, RoundingMode.HALF_UP));


        // Splitting totals by food type
        /*
        Vegetables (26%)
        Fruit (8%)
        Proteins (26%)
        Fats (11%)
        Starches (21%)
        Dairy (8%)
         */
        BigDecimal daily_vege_male = BigDecimal.valueOf(C16 * ((double) 26 / 100));
        //res.put("daily_vege_male", daily_vege_male.setScale(7, RoundingMode.HALF_UP));
        BigDecimal daily_fruit_male = BigDecimal.valueOf(C16 * ((double) 8 / 100));
        //res.put("daily_fruit_male", daily_fruit_male.setScale(7, RoundingMode.HALF_UP));
        BigDecimal daily_proteins_male = BigDecimal.valueOf(C16 * ((double) 26 / 100));
        //res.put("daily_proteins_male", daily_proteins_male.setScale(7, RoundingMode.HALF_UP));
        BigDecimal daily_fats_male = BigDecimal.valueOf(C16 * ((double) 11 / 100));
        //res.put("daily_fats_male", daily_fats_male.setScale(7, RoundingMode.HALF_UP));
        BigDecimal daily_starches_male = BigDecimal.valueOf(C16 * ((double) 21 / 100));
        //res.put("daily_starches_male", daily_starches_male.setScale(7, RoundingMode.HALF_UP));
        BigDecimal daily_dairy_male = BigDecimal.valueOf(C16 * ((double) 8 / 100));
        //res.put("daily_dairy_male", daily_dairy_male.setScale(7, RoundingMode.HALF_UP));
        //res.put("total_daily_male", daily_dairy_male.add(daily_fats_male.add(daily_fruit_male.add(daily_starches_male.add(daily_proteins_male.add(daily_vege_male))))).setScale(7, RoundingMode.HALF_UP));


        BigDecimal daily_vege_female = BigDecimal.valueOf(D16 * ((double) 26 / 100));
        res.put("daily_vege_female", daily_vege_female.setScale(7, RoundingMode.HALF_UP));
        BigDecimal daily_fruit_female = BigDecimal.valueOf(D16 * ((double) 8 / 100));
        res.put("daily_fruit_female", daily_fruit_female.setScale(7, RoundingMode.HALF_UP));
        BigDecimal daily_proteins_female = BigDecimal.valueOf(D16 * ((double) 26 / 100));
        res.put("daily_proteins_female", daily_proteins_female.setScale(7, RoundingMode.HALF_UP));
        BigDecimal daily_fats_female = BigDecimal.valueOf(D16 * ((double) 11 / 100));
        res.put("daily_fats_female", daily_fats_female.setScale(7, RoundingMode.HALF_UP));
        BigDecimal daily_starches_female = BigDecimal.valueOf(D16 * ((double) 21 / 100));
        res.put("daily_starches_female", daily_starches_female.setScale(7, RoundingMode.HALF_UP));
        BigDecimal daily_dairy_female = BigDecimal.valueOf(D16 * ((double) 8 / 100));
        res.put("daily_dairy_female", daily_dairy_female.setScale(7, RoundingMode.HALF_UP));
        res.put("total_daily_female", daily_dairy_female.add(daily_fats_female.add(daily_fruit_female.add(daily_starches_female.add(daily_proteins_female.add(daily_vege_female))))).setScale(7, RoundingMode.HALF_UP));


        return res;
    }
}
