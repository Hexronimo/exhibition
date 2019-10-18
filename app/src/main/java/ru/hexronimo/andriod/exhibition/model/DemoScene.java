package ru.hexronimo.andriod.exhibition.model;

import android.net.Uri;

import ru.hexronimo.andriod.exhibition.R;

public class DemoScene {
    public static Scene createDemo() {
        int resID = R.drawable.testscene;
        String image = Uri.parse("android.resource://" + R.class.getPackage().getName() + resID).toString();
        Scene demoScene = new Scene(image,
                "Demo Scene",
                "DEMO Scene",
                "You will no longer see this demo after adding real scenes to Exhibition");

        Content content = new SimpleTextContent();
        Content content1 = new SimpleTextContent();

        content.setTitle("Simple unformated text");
        content1.setTitle("Html and poetry");


        content.setTextContent("Wow! I think I am 101% in love with you. Can I be so bold to invite you to study with me Saturday afternoon and after, invite you to go to the movies and then, invite you to go to dinner and then, invite you to go dancing and then, if you were not tired of my lack of objectivity, ask you a kiss?\n" +
                "\n" + "    Answer, please, or shorten the process by giving me this kiss at once!");
        content1.setTextContent(
                "<p>It's nine o'clock on a saturday<br>" +
                "Regular crowd shuffles in<br>" +
                "There's an old man sittin' next to me<br>" +
                "Makin' love to his tonic and gin</p>" +

                "<p>He says son can you play me a memory?<br>" +
                "I'm not really sure how it goes<br>" +
                "But it's sad and it's sweet and I knew it complete<br>" +
                "When I wore a younger man's clothes</p>" +

                "<p>La-la-la de-de da<br>" +
                "La-la de-de da da-da</p>" +

                "<p>Sing us a song you're the piano man<br>" +
                "Sing us a song tonight<br>" +
                "Well we're all in the mood for a melody<br>" +
                "And you've got us feelin' alrigh</p>");

        demoScene.addPoint(new Point(1452/3080f, 617/1450f));
        demoScene.addPoint(new Point(820/3080f, 951/1450f));
        demoScene.addPoint(new Point(2060/3080f, 969/1450f, content));
        demoScene.addPoint(new Point(1276/3080f, 414/1450f, content1));
        demoScene.addPoint(new Point(2510/3080f, 618/1450f));
        demoScene.addPoint(new Point(283/3080f, 807/1450f));
        demoScene.addPoint(new Point(1291/3080f, 1053/1450f));

        return demoScene;
    }
}
