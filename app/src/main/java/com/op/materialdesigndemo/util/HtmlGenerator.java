package com.op.materialdesigndemo.util;

import com.op.materialdesigndemo.entity.StoryDetail;

import java.util.List;

public class HtmlGenerator {
    //css样式,隐藏header
    private static final String HIDE_HEADER_STYLE = "<style>div.headline{display:none;}</style>";
    //css style tag,需要格式化
    private static final String NEEDED_FORMAT_CSS_TAG = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"/>";

    // js script tag,需要格式化
    private static final String NEEDED_FORMAT_JS_TAG = "<script src=\"%s\"></script>";

    public static String generateHtml(StoryDetail storyDetail) {
        String css = generateCss(storyDetail.getCss());
        String js = generateJs(storyDetail.getJs());

        StringBuilder html = new StringBuilder();
        html.append("<html><head>")
                .append(css)
                .append(HIDE_HEADER_STYLE)
                .append(js)
                .append("</head>")

                .append("<body>")
                .append(storyDetail.getBody())
                .append("</body>")

                .append("</html>");
        return html.toString();

    }

    private static String generateCss(List<String> cssUrls) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String url : cssUrls) {
            stringBuilder.append(String.format(NEEDED_FORMAT_CSS_TAG, url));
        }
        return stringBuilder.toString();
    }

    private static String generateJs(List<String> jsUrls) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String url : jsUrls) {
            stringBuilder.append(String.format(NEEDED_FORMAT_JS_TAG, url));
        }
        return stringBuilder.toString();
    }
}
