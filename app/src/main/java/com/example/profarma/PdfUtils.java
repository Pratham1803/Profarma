package com.example.profarma;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfUtils {

    public static void generatePdfFromView(View view, String fileName) {
        // Create a new PdfDocument
        PdfDocument document = new PdfDocument();

        // Create a page description
        NestedScrollView scroll = (NestedScrollView) view;
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(view.getWidth(), scroll.computeVerticalScrollRange(), 1).create();

        // Start a page
        PdfDocument.Page page = document.startPage(pageInfo);

        // Get the Canvas from the page
        Canvas canvas = page.getCanvas();

        // Draw the view on the canvas
        view.draw(canvas);

        // Finish the page
        document.finishPage(page);

        // Save the document
        try {
            File pdfDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS), "ProFarma");
            if (!pdfDir.exists()) {
                pdfDir.mkdir();
            }
            File file = new File(pdfDir, fileName + ".pdf");
            FileOutputStream fos = new FileOutputStream(file);
            document.writeTo(fos);
            fos.close();

            Toast.makeText(view.getContext(), "PDF saved to document/ProFarma/" + fileName, Toast.LENGTH_LONG).show();

            // Share the PDF file via WhatsApp
            Uri uri = FileProvider.getUriForFile(
                    view.getContext(),
                    view.getContext().getPackageName() + ".provider",
                    file
            );

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setDataAndType(uri, "application/pdf");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.setPackage("com.whatsapp");
            view.getContext().startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ErrorMsg", "generatePdfFromView: " + e.getMessage());
            Toast.makeText(view.getContext(), "Error creating PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            document.close();
        }
    }
}