package ru.hexronimo.andriod.exhibition.model;

import android.net.Uri;

import ru.hexronimo.andriod.exhibition.R;

public class DemoScene {
    public static Scene createDemo() {
        int resID = R.drawable.testscene;
        String image = Uri.parse("android.resource://" + R.class.getPackage().getName() + resID).toString();
        Scene demoScene = new Scene(image,
                "Demo Scene",
                "DEMO Scene");

        // jyst text 1
        Content content = new SimpleTextContent(R.layout.activity_content_ver1);
        content.setTitle("Unformatted text");
        content.setTextContent("Wow! I think I am 101% in love with you. Can I be so bold to invite you to study with me Saturday afternoon and after, invite you to go to the movies and then, invite you to go to dinner and then, invite you to go dancing and then, if you were not tired of my lack of objectivity, ask you a kiss?\n"
                 + "Answer, please, or shorten the process by giving me this kiss at once!");


        // just text 2
        Content content1 = new SimpleTextContent(R.layout.activity_content_ver2);
        content1.setTitle("Simple Html and poetry. Layout 2");
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


        // simple image (center scaled)
        int image1ID = R.drawable.demo01;
        Uri image1 = Uri.parse("android.resource://" + R.class.getPackage().getName()+"/drawable/demo01");
        Content content2 = new SimpleImageContent(R.layout.activity_content_ver1,image1);
        content2.setTextContent("" +
                "<resources><p>Photo by Steve Johnson from Pexels</p>" +
                "An image (jpg or png with transparency) can be placed as content. You still can add any text under it.<br>" +
                "Pay attention to image size, before uploading you better scale it by yourself according to your tablet's screen size.</resources>");

        // simple image (full screen)
        Uri image4 = Uri.parse("android.resource://" + R.class.getPackage().getName()+"/drawable/demo03");
        Content content6 = new SimpleImageContent(R.layout.activity_content_ver4,image4);
        content6.setTitle("" +
                "Fullscreen image. Layout 4");
        content6.setTextContent("" +
                "<resources><p>Photo by Kaboompics .com from Pexels</p>" +
                "You can set image as background, but text will be white, so you need to choose dark image.<br>" +
                "Pay attention to image size, before uploading you better scale it by yourself according to your tablet's screen size.</resources>");

        // audio
        Uri media1 = Uri.parse("android.resource://" + R.class.getPackage().getName()+"/raw/audio_sample");
        Uri image2 = Uri.parse("android.resource://" + R.class.getPackage().getName()+"/drawable/demo02");
        Content content3 = new SimpleAudioContent(R.layout.activity_content_audio, media1, image2, false);
        content3.setTitle("Audio file");
        content3.setTextContent("<resources><p>Photo by Steve Johnson from Pexels</p>" +
                "<p>You can add speech or music, image and text can be added along with it.</p></resources>");


        // video
        Uri video1 = Uri.parse("android.resource://" + R.class.getPackage().getName()+"/raw/video_sample");
        Content content4 = new SimpleVideoContent(R.layout.activity_content_video, video1, false);
        content4.setTitle("Simple video");
        content4.setTextContent("<resources>" +
                "Video by <a href='https://www.pexels.com/@kml-1179532?utm_content=attributionCopyText&utm_medium=referral&utm_source=pexels'>KML</a> from Pexels<br>" +
                "<p>Android currently supports the following video formats:<br>" +
                "    H.263<br>" +
                "    H.264 AVC<br>" +
                "    MPEG-4 SP<br>" +
                "    VP8</p>" +
                "</resources>"
        );


        // small picture with text at right side
        Content content5 = new SimpleImageContent(R.layout.activity_content_ver2, image2);
        content5.setTitle("Image and text. Layout 2");
        content5.setTextContent("<resources><p>Photo by Steve Johnson from Pexels</p><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis sem libero, molestie non volutpat et, iaculis id felis. Nam egestas eu metus vitae pulvinar. Quisque aliquet nulla sit amet quam pulvinar consequat. Ut vitae elit pretium, viverra dolor eu, efficitur mauris. Nulla eget pellentesque mauris. Proin sollicitudin auctor mi, egestas bibendum tortor porta non. Maecenas aliquam mollis pulvinar. Vivamus purus est, ullamcorper et eros sed, hendrerit ornare ligula. Morbi ut blandit justo. Quisque et odio suscipit, pulvinar sapien ac, facilisis ex. Integer felis eros, tempor ut odio in, congue porttitor ligula. Etiam ultricies ex lorem, nec egestas quam aliquet a. Vestibulum metus lorem, elementum sed sagittis eu, scelerisque rhoncus nibh.</p>" +
                "<p>Nunc vitae interdum nulla. Vivamus suscipit leo massa, sit amet porttitor urna malesuada vel. Nulla scelerisque quam vel velit eleifend, in iaculis urna tempus. Nullam nulla eros, aliquet nec lacus id, vulputate placerat lacus. Quisque varius sem sapien, vel ornare orci condimentum ac. In ornare orci turpis, ut egestas lectus ultricies vitae. Maecenas vulputate vulputate scelerisque. Curabitur tincidunt ultrices eros eu malesuada. Quisque finibus, eros suscipit semper ornare, nunc magna vestibulum mi, quis ultrices quam tellus at ex. Proin pellentesque metus vel neque fringilla commodo. Maecenas eget leo quis ligula ultrices pulvinar eget sed magna. Cras fringilla orci a libero imperdiet, at varius ante eleifend. Aliquam venenatis lacus a vestibulum laoreet. Vivamus laoreet rhoncus purus et efficitur.</p></resources>");

        demoScene.addPoint(new Point(1452/3080f, 617/1450f, content5));
        demoScene.addPoint(new Point(820/3080f, 951/1450f, content4));
        demoScene.addPoint(new Point(2060/3080f, 969/1450f, content));
        demoScene.addPoint(new Point(1276/3080f, 414/1450f, content3));
        demoScene.addPoint(new Point(2510/3080f, 618/1450f, content2));
        demoScene.addPoint(new Point(283/3080f, 807/1450f, content1));
        demoScene.addPoint(new Point(1291/3080f, 1053/1450f, content6));

        demoScene.setInfo("Click on blue points to see all kinds of supported content.");

        return demoScene;
    }
}
