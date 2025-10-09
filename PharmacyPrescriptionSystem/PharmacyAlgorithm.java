package PharmacyPrescriptionSystem;

import java.util.*;

public class PharmacyAlgorithm {

    private HashMap<String, List<Medicine>> medInventory;
    private final Random rand;

    // Constructor
    public PharmacyAlgorithm() {
        this.medInventory = new HashMap<>();
        this.rand = new Random();
        loadInventory();
    }
    // Load inventory with medicines
    private void loadInventory() {
        String[][] conditions = {
                {"Headache", "Ibuprofen", "Paracetamol", "Aspirin", "Naproxen", "Diclofenac"},
                {"Fever", "Paracetamol", "Ibuprofen", "Acetaminophen", "Aspirin", "Mefenamic Acid"},
                {"Cold", "Phenylephrine", "Pseudoephedrine", "Cetirizine", "Chlorpheniramine", "Loratadine"},
                {"Cough", "Dextromethorphan", "Guaifenesin", "Ambroxol"},
                {"Allergy", "Loratadine", "Cetirizine", "Fexofenadine"},
                {"Asthma", "Albuterol", "Budesonide", "Fluticasone"},
                {"Diabetes", "Metformin", "Glipizide", "Insulin"},
                {"Hypertension", "Lisinopril", "Amlodipine", "Losartan"},
                {"Anxiety", "Alprazolam", "Diazepam", "Lorazepam"},
                {"Depression", "Sertraline", "Fluoxetine", "Escitalopram"},
                {"Insomnia", "Melatonin", "Zolpidem", "Trazodone"},
                {"Arthritis", "Naproxen", "Celecoxib", "Diclofenac"},
                {"Acid Reflux", "Omeprazole", "Pantoprazole", "Esomeprazole"},
                {"Nausea", "Ondansetron", "Metoclopramide", "Dimenhydrinate"},
                {"Diarrhea", "Loperamide", "Bismuth Subsalicylate", "Racecadotril"},
                {"Constipation", "Docusate", "Lactulose", "Psyllium"},
                {"Migraine", "Sumatriptan", "Rizatriptan", "Propranolol"},
                {"Infection", "Amoxicillin", "Azithromycin", "Cefalexin"},
                {"Pain", "Tramadol", "Oxycodone", "Morphine"},
                {"Inflammation", "Prednisone", "Dexamethasone", "Hydrocortisone"},
                {"Cholesterol", "Atorvastatin", "Simvastatin", "Rosuvastatin"},
                {"Blood Clot", "Warfarin", "Apixaban", "Rivaroxaban"},
                {"Pneumonia", "Levofloxacin", "Ceftriaxone", "Azithromycin"},
                {"UTI", "Ciprofloxacin", "Nitrofurantoin", "Amoxicillin"},
                {"Skin Rash", "Hydrocortisone", "Triamcinolone", "Clobetasol"},
                {"Eczema", "Betamethasone", "Tacrolimus", "Mometasone"},
                {"Fungal Infection", "Fluconazole", "Clotrimazole", "Ketoconazole"},
                {"Herpes", "Acyclovir", "Valacyclovir", "Famciclovir"},
                {"Seizures", "Carbamazepine", "Valproate", "Levetiracetam"},
                {"Thyroid", "Levothyroxine", "Liothyronine", "Methimazole"},
                {"Osteoporosis", "Alendronate", "Risedronate", "Calcitonin"},
                {"Gout", "Allopurinol", "Colchicine", "Febuxostat"},
                {"Menopause", "Estrogen", "Progesterone", "Raloxifene"},
                {"Birth Control", "Ethinyl Estradiol", "Levonorgestrel", "Drospirenone"},
                {"Erectile Dysfunction", "Sildenafil", "Tadalafil", "Vardenafil"},
                {"Glaucoma", "Latanoprost", "Timolol", "Brimonidine"},
                {"Dry Eyes", "Cyclosporine", "Lifitegrast", "Artificial Tears"},
                {"Anemia", "Ferrous Sulfate", "Folic Acid", "Vitamin B12"},
                {"Vitamin Deficiency", "Vitamin D", "Vitamin C", "Multivitamins"},
                {"ADHD", "Methylphenidate", "Amphetamine", "Atomoxetine"},
                {"Bipolar Disorder", "Lithium", "Valproate", "Lamotrigine"},
                {"Schizophrenia", "Risperidone", "Olanzapine", "Clozapine"},
                {"OCD", "Clomipramine", "Fluvoxamine", "Sertraline"},
                {"PTSD", "Paroxetine", "Venlafaxine", "Sertraline"},
                {"Smoking Cessation", "Varenicline", "Bupropion", "Nicotine Patch"},
                {"Weight Loss", "Orlistat", "Phentermine", "Liraglutide"},
                {"Acne", "Isotretinoin", "Adapalene", "Clindamycin"},
                {"Hair Loss", "Finasteride", "Minoxidil", "Dutasteride"},
                {"Vertigo", "Meclizine", "Betahistine", "Dimenhydrinate"},
                {"Muscle Spasm", "Cyclobenzaprine", "Baclofen", "Methocarbamol"}
        };
        String[] manufacturers = {
                "Pfizer", "Johnson & Johnson", "Merck", "Novartis", "GSK",
                "Sanofi", "Roche", "AstraZeneca", "AbbVie", "Bayer"
        };
        for (String[] c : conditions) {
            String key = normalizeKey(c[0]);
            List<Medicine> meds = new ArrayList<>();

            for (int i = 1; i < c.length; i++) {
                meds.add(new Medicine(
                        c[i],
                        c[i],
                        rand.nextInt(100) + 50,
                        "2026-" + String.format("%02d", rand.nextInt(12) + 1) + "-15",
                        manufacturers[rand.nextInt(manufacturers.length)],
                        Math.round((rand.nextDouble() * 50 + 10) * 100.0) / 100.0
                ));
            }
            medInventory.put(key, meds);
        }
    }
    // Normalize key for consistent hashing
    private String normalizeKey(String condition) {
        if (condition == null || condition.isBlank()) {
            return "";
        }
        return condition.trim().toLowerCase();
    }
    // Modern Hashing Implementation
    public Optional<List<Medicine>> searchMedicine(String condition) {
        if (condition == null || condition.isBlank()) {
            return Optional.empty();
        }
        String key = normalizeKey(condition);
        List<Medicine> result = medInventory.get(key);

        return Optional.ofNullable(result);
    }
    // Find medicine by condition
    public List<Medicine> findMedicineByCondition(String condition) {

        Optional<List<Medicine>> result = searchMedicine(condition);
        if (result.isPresent()) {
            List<Medicine> meds = result.get();
            for (Medicine m : meds) {
            }return meds;
        } else {
            return Collections.emptyList();
        }
    }
    // Get hashing complexity explanation
    public String getHashingComplexity() {
        return "\nHashMap Lookup — Average Case: O(1), Worst Case: O(n)\n";
    }
    // Get all available conditions
    public Set<String> getAllConditions() {
        return medInventory.keySet();
    }
    // Get sorted list of conditions (useful for GUI)
    public List<String> getSortedConditions() {
        List<String> conditions = new ArrayList<>(medInventory.keySet());
        Collections.sort(conditions);
        return conditions;
    }
    // Check if condition exists
    public boolean hasCondition(String condition) {
        String key = normalizeKey(condition);
        return medInventory.containsKey(key);
    }
    // Get total number of medicines across all conditions
    public int getTotalMedicineCount() {
        return medInventory.values().stream()
                .mapToInt(List::size)
                .sum();
    }
    // Get the HashMap (if needed for external access)
    public HashMap<String, List<Medicine>> getMedInventory() {
        return new HashMap<>(medInventory);
    }
}