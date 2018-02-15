package com.aresWikipediaRelationsExtractor;

import com.google.cloud.language.v1.*;

public class AnalyzeGoogleNLP {

    public static void main(String[] args) throws Exception {
        String sentence = "The government signed a revised peace agreement with the Farc in November after four years of negotiations in the Cuban capital Havana.";
        analyzeEntitiesText(sentence);
    }

    public static void analyzeEntitiesText(String text) throws Exception {
        // [START analyze_entities_text]
        // Instantiate the Language client com.google.cloud.language.v1.LanguageServiceClient
        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            Document doc = Document.newBuilder()
                    .setContent(text)
                    .setType(Document.Type.PLAIN_TEXT)
                    .build();
            AnalyzeEntitiesRequest request = AnalyzeEntitiesRequest.newBuilder()
                    .setDocument(doc)
                    .setEncodingType(EncodingType.UTF16)
                    .build();

            AnalyzeEntitiesResponse response = language.analyzeEntities(request);

            // Print the response
            for (Entity entity : response.getEntitiesList()) {
                System.out.println(entity.getName() + "--> " + entity.getType());
//                System.out.printf("Entity: %s", entity.getName());
//                System.out.printf("Entity: %s", entity.getType());
//                System.out.printf("Salience: %.3f\n", entity.getSalience());
//                System.out.println("Metadata: ");
//                for (Map.Entry<String, String> entry : entity.getMetadataMap().entrySet()) {
//                    System.out.printf("%s : %s", entry.getKey(), entry.getValue());
//                }
//                for (EntityMention mention : entity.getMentionsList()) {
//                    System.out.printf("Begin offset: %d\n", mention.getText().getBeginOffset());
//                    System.out.printf("Content: %s\n", mention.getText().getContent());
//                    System.out.printf("Type: %s\n\n", mention.getType());
//                }
            }
        }
        // [END analyze_entities_text]
    }


}

