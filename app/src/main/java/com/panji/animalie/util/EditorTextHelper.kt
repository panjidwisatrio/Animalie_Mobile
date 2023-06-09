package com.panji.animalie.util

import android.content.Context
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import com.panji.animalie.R
import jp.wasabeef.richeditor.RichEditor

object EditorTextHelper {

    fun setEditorWasabeef(
        editor: RichEditor,
        undo: ImageButton,
        redo: ImageButton,
        bold: ImageButton,
        italic: ImageButton,
        underline: ImageButton,
        strike: ImageButton,
        heading1: ImageButton,
        heading2: ImageButton,
        heading3: ImageButton,
        heading4: ImageButton,
        indent: ImageButton,
        outdent: ImageButton,
        alignLeft: ImageButton,
        alignCenter: ImageButton,
        alignRight: ImageButton,
        blockquote: ImageButton,
        insertBullets: ImageButton,
        insertNumbers: ImageButton,
        insertLink: ImageButton,
        bgColor: ImageButton,
        txtColor: ImageButton,
        context: Context?
    ) {
        editor.setEditorHeight(200)
        editor.setPadding(16, 10, 16, 10)
        editor.setPlaceholder("Write your post here...")
        editor.setInputEnabled(true)

        undo.setOnClickListener {
            editor.undo()
        }

        redo.setOnClickListener {
            editor.redo()
        }

        bold.setOnClickListener {
            editor.setBold()
        }

        italic.setOnClickListener {
            editor.setItalic()
        }

        underline.setOnClickListener {
            editor.setUnderline()
        }

        strike.setOnClickListener {
            editor.setStrikeThrough()
        }

        heading1.setOnClickListener {
            editor.setHeading(1)
        }

        heading2.setOnClickListener {
            editor.setHeading(2)
        }

        heading3.setOnClickListener {
            editor.setHeading(3)
        }

        heading4.setOnClickListener {
            editor.setHeading(4)
        }

        indent.setOnClickListener {
            editor.setIndent()
        }

        outdent.setOnClickListener {
            editor.setOutdent()
        }

        alignLeft.setOnClickListener {
            editor.setAlignLeft()
        }

        alignCenter.setOnClickListener {
            editor.setAlignCenter()
        }

        alignRight.setOnClickListener {
            editor.setAlignRight()
        }

        blockquote.setOnClickListener {
            editor.setBlockquote()
        }

        insertBullets.setOnClickListener {
            editor.setBullets()
        }

        insertNumbers.setOnClickListener {
            editor.setNumbers()
        }

        insertLink.setOnClickListener {
            editor.insertLink("ngatour.id", "ngatour.id")
        }

        bgColor.setOnClickListener {
            ContextCompat.getColor(context!!, R.color.md_theme_dark_onBackground)
        }

        txtColor.setOnClickListener {
            ContextCompat.getColor(context!!, R.color.md_theme_dark_onBackground)
        }
    }
}