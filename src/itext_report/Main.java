package itext_report;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfWriter;

public class Main {

	private static String data[][] = {
			{"45460", "足跡"},
			{"48134", "新しい歌の味ひ"},
			{"48164", "A LETTER FROM PRISON"},
			{"48154", "郁雨に与ふ"},
			{"816", "一握の砂"},
			{"48133", "「一握の砂」広告"},
			{"48135", "一日中の楽しき時刻"},
			{"52298", "公孫樹"},
			{"43031", "一利己主義者と友人との対話"},
			{"48136", "いろ／＼の言葉と人"},
			{"48155", "所謂今度の事"},
			{"48156", "歌のいろ／＼"},
			{"48137", "歌集「嘲笑」序文"},
			{"43070", "火星の芝居"},
			{"49676", "悲しき思出"},
			{"815", "悲しき玩具"},
			{"48157", "硝子窓"},
			{"49677", "閑天地"},
			{"45461", "菊池君"},
			{"45462", "雲は天才である"},
			{"48138", "雲間寸観"},
			{"48366", "刑余の叔父"},
			{"811", "氷屋の旗"},
			{"47891", "心の姿の研究"},
			{"45463", "札幌"},
			{"50410", "散文詩"},
			{"817", "詩"},
			{"814", "時代閉塞の現状"},
			{"49678", "渋民村より"},
			{"50411", "唱歌"}};

	public static void main(String[] args) throws Throwable {

		FileOutputStream fos = new FileOutputStream("output.pdf");
		Document doc = new Document();
		PdfWriter writer = PdfWriter.getInstance(doc, fos);

		// タイトル,作者の設定
		doc.addTitle("石川啄木作品リスト");
		doc.addAuthor("shuetsu");

		// 印刷時に「実際のサイズ」となるようにする。
		writer.addViewerPreference(PdfName.PRINTSCALING, PdfName.NONE);

		doc.open();

		// ページの設定。
		doc.setPageSize(PageSize.A4);
		doc.setMargins(0, 0, 0, 0);
		doc.newPage();

		PdfContentByte cb = writer.getDirectContent();

		// ベースフォントの作成。
		BaseFont font = BaseFont.createFont("HeiseiKakuGo-W5", "UniJIS-UCS2-H", BaseFont.NOT_EMBEDDED);

		// MSゴシックを埋め込む場合。
		//BaseFont font = BaseFont.createFont("C:\\Windows\\Fonts\\msgothic.ttc,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

		// タイトルの描画。
		{
			cb.saveState();

			// ストロークを描画することで、太字にする。
			cb.setLineWidth(0.2);
			cb.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);

			// フォントとフォントサイズの設定。
			cb.setFontAndSize(font, 20);

			// タイトル文字の描画。
			cb.beginText();
			cb.setTextMatrix(1f, 0f, 0.3f, 1f, // 変換行列を指定して斜体にしてみる。
					200,
					doc.getPageSize().getHeight() - 50); // Y座標はページ下端を基準として指定する。
			cb.showText("石川啄木作品リスト");
			cb.endText();
			cb.restoreState();
		}

		// 明細の高さ。
		float h = 20;

		// 背景の描画。
		for(int i = 0;i < data.length;i++){

			float y = doc.getPageSize().getHeight() - (i * h + 100);

			// 1行おきに灰色で背景を塗りつぶす。
			if (i % 2 == 0){
				cb.saveState();
				cb.setRGBColorFill(200, 200, 200);
				cb.rectangle(100, y, 400, h);
				cb.fill();
				cb.restoreState();
			}
		}

		// 明細の描画。
		for(int i = 0;i < data.length;i++){

			float y = doc.getPageSize().getHeight() - (i * h + 100);

			// 明細区切りの直線を描画。
			{
				cb.saveState();
				cb.moveTo(100, y);
				cb.lineTo(500, y);
				cb.stroke();
				cb.restoreState();
			}

			// 明細の内容を描画。
			{
				cb.saveState();
				cb.setFontAndSize(font, 10);
				cb.beginText();

				cb.setTextMatrix(100, y + 5);
				cb.showText("作品ID : " + data[i][0]);

				cb.setTextMatrix(250, y + 5);
				cb.showText(data[i][1]);

				cb.endText();
				cb.restoreState();
			}
		}

		doc.close();
	}

}
