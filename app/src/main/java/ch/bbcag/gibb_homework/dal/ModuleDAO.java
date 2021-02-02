package ch.bbcag.gibb_homework.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import ch.bbcag.gibb_homework.database.ModuleEntry;
import ch.bbcag.gibb_homework.helper.DatabaseHelper;
import ch.bbcag.gibb_homework.model.Module;

public class ModuleDAO {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    public ModuleDAO(Context ctx) {
        dbHelper = new DatabaseHelper(ctx);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Module> all() {

        String[] projectionModule = {
                ModuleEntry.COLUMN_ID,
                ModuleEntry.COLUMN_TITLE,
                ModuleEntry.COLUMN_NUMBER,
                ModuleEntry.COLUMN_COLOR,
                ModuleEntry.COLUMN_IS_ACTIVE
        };

        String sortOrderModule = String.format("%s ASC", ModuleEntry.COLUMN_NUMBER);
        sortOrderModule = ModuleEntry.COLUMN_NUMBER + " ASC";

        Cursor cursorModule = db.query(
                false,
                ModuleEntry.TABLE_NAME, // tableName
                projectionModule, // columns
                null,
                null,
                null,
                null,
                sortOrderModule, // orderBy
                null,
                null
        );

        ArrayList<Module> result = new ArrayList<Module>();
        while (cursorModule.moveToNext()) {
            Module module = new Module();
            module.setId(cursorModule.getInt(cursorModule.getColumnIndex(ModuleEntry.COLUMN_ID)));
            module.setTitle(cursorModule.getString(cursorModule.getColumnIndex(ModuleEntry.COLUMN_TITLE)));
            module.setNumber(cursorModule.getString(cursorModule.getColumnIndex(ModuleEntry.COLUMN_NUMBER)));
            module.setColor(cursorModule.getString(cursorModule.getColumnIndex(ModuleEntry.COLUMN_COLOR)));
            module.setActive(cursorModule.getInt(cursorModule.getColumnIndex(ModuleEntry.COLUMN_IS_ACTIVE)) > 0);

            result.add(module);
        }

        Log.d("DATABASE", "Hallo: " + result);
        cursorModule.close();

        return result;
    }

    public ArrayList<Module> allActiveModules() {

        String[] projectionModule = {
                ModuleEntry.COLUMN_ID,
                ModuleEntry.COLUMN_TITLE,
                ModuleEntry.COLUMN_NUMBER,
                ModuleEntry.COLUMN_COLOR,
                ModuleEntry.COLUMN_IS_ACTIVE
        };

        String sortOrderModule = String.format("%s ASC", ModuleEntry.COLUMN_NUMBER);
        sortOrderModule = ModuleEntry.COLUMN_NUMBER + " ASC";

        String selection = String.format("%s = ?", ModuleEntry.COLUMN_IS_ACTIVE);
        String[] selectionArgs = {"1"};

        Cursor cursorModul = db.query(
                false,
                ModuleEntry.TABLE_NAME, // tableName
                projectionModule, // columns
                selection,
                selectionArgs,
                null,
                null,
                sortOrderModule, // orderBy
                null,
                null
        );

        ArrayList<Module> result = new ArrayList<Module>();
        while (cursorModul.moveToNext()) {
            Module module = new Module();
            module.setId(cursorModul.getInt(cursorModul.getColumnIndex(ModuleEntry.COLUMN_ID)));
            module.setTitle(cursorModul.getString(cursorModul.getColumnIndex(ModuleEntry.COLUMN_TITLE)));
            module.setNumber(cursorModul.getString(cursorModul.getColumnIndex(ModuleEntry.COLUMN_NUMBER)));
            module.setColor(cursorModul.getString(cursorModul.getColumnIndex(ModuleEntry.COLUMN_COLOR)));
            module.setActive(cursorModul.getInt(cursorModul.getColumnIndex(ModuleEntry.COLUMN_IS_ACTIVE)) > 0);

            result.add(module);
        }

        Log.d("DATABASE", "Hallo: " + result);
        cursorModul.close();

        return result;

    }

    public void updateIsActive(Module module, boolean activeState) {
        ContentValues values = new ContentValues();
        values.put(ModuleEntry.COLUMN_IS_ACTIVE, activeState);

        String selection = String.format("%s = ?", ModuleEntry.COLUMN_ID);
        String[] selectionArgs = {String.valueOf(module.getId())};

        db.update(
                ModuleEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }
}
