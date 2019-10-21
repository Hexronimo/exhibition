package ru.hexronimo.andriod.exhibition.model;

import android.app.Application;
import android.net.Uri;

import java.io.File;

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
        content1.setTitle("Simple Html and poetry");


        content.setTextContent("Wow! I think I am 101% in love with you. Can I be so bold to invite you to study with me Saturday afternoon and after, invite you to go to the movies and then, invite you to go to dinner and then, invite you to go dancing and then, if you were not tired of my lack of objectivity, ask you a kiss?\n"
                 + "Answer, please, or shorten the process by giving me this kiss at once!");
        content1.setTextContent(
                "<resources><p>It's nine o'clock on a saturday<br>" +
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
                "And you've got us feelin' alrigh</p></resources>");

        int image1ID = R.drawable.demo01;
        Uri image1 = Uri.parse("android.resource://" + R.class.getPackage().getName()+"/drawable/demo01");
        Content content2 = new SimpleImageContent(image1);
        content2.setTextContent("Their love was so deep...");

        Uri media1 = Uri.parse("android.resource://" + R.class.getPackage().getName()+"/raw/audio_sample");
        Uri image2 = Uri.parse("android.resource://" + R.class.getPackage().getName()+"/drawable/chandelure");
        Content content3 = new SimpleMediaContent(media1, image2, true);
        content3.setTitle("Some audio");

        content3.setTextContent("You may add speech or music to content, image and text can be added too, also you can set autoplay (start playing after open), otherwise audio will play after pressing a button (default).");

        demoScene.addPoint(new Point(1452/3080f, 617/1450f));
        demoScene.addPoint(new Point(820/3080f, 951/1450f));
        demoScene.addPoint(new Point(2060/3080f, 969/1450f, content));
        demoScene.addPoint(new Point(1276/3080f, 414/1450f, content3));
        demoScene.addPoint(new Point(2510/3080f, 618/1450f, content2));
        demoScene.addPoint(new Point(283/3080f, 807/1450f, content1));
        demoScene.addPoint(new Point(1291/3080f, 1053/1450f));

        demoScene.setInfo("Click on blue points to see all kinds of supported content.");

        return demoScene;
    }
}