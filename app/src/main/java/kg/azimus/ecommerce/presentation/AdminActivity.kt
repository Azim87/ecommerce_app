package kg.azimus.ecommerce.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.yalantis.ucrop.UCrop
import kg.azimus.ecommerce.R
import kg.azimus.ecommerce.util.ActivityHelper
import kg.azimus.ecommerce.util.toast
import kotlinx.android.synthetic.main.activity_admin.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


private const val TAG = "AdminActivity"
private const val INTENT_TYPE = "image/*"
private const val GALLERY_REQUEST_CODE = 1

class AdminActivity : AppCompatActivity() {

    private lateinit var mStorage: StorageReference
    private lateinit var mDataBaseReference: DatabaseReference
    private lateinit var saveCurrentDate: String
    private lateinit var saveCurrentTime: String

    private var imageUri: Uri? = null
    private var downLoadImageUri: String? = null
    private var category: String? = null
    private var productRandomKey: String? = null
    private var productName: String? = null
    private var productDescription: String? = null
    private var productPrice: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        Log.d(TAG, "onCreate: start Admin activity")

        mStorage = FirebaseStorage.getInstance().reference.child("Product images")
        val dataBase = FirebaseDatabase.getInstance()
        mDataBaseReference = dataBase.reference.child("Products")

        getCategoryIntent()
        onInputProductClick()
        addNewProduct()
    }

    private fun getCategoryIntent() {
        category = intent.extras?.get("category").toString()
        toast(this, category.toString())
    }

    private fun onInputProductClick() {
        select_product_image.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent()
        galleryIntent.action = Intent.ACTION_GET_CONTENT
        galleryIntent.type = INTENT_TYPE
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.data
            startCrop(imageUri!!)
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            imageUri= UCrop.getOutput(data!!)
            select_product_image.setImageURI(imageUri!!)
        }
    }

    private fun startCrop(clientUri: Uri) {
        var fileName = "sampleCroping"
        fileName += ".jpg"
        fileName += ".png"
        val uCrop =
            UCrop.of(clientUri, Uri.fromFile(File(cacheDir, fileName)))
        uCrop.withAspectRatio(1f, 1f)
        uCrop.withAspectRatio(3f, 4f)
        uCrop.withAspectRatio(16f, 9f)
        uCrop.withAspectRatio(2f, 3f)
        uCrop.useSourceImageAspectRatio()
        uCrop.withMaxResultSize(1200, 1200)
        uCrop.withOptions(getOptions()!!)
        uCrop.start(this@AdminActivity)
    }

    private fun getOptions(): UCrop.Options? {
        val options = UCrop.Options()
        options.setFreeStyleCropEnabled(true)
        options.setCompressionQuality(70)
        options.setLogoColor(Color.BLUE)
        return options
    }

    private fun addNewProduct() {
        add_new_product.setOnClickListener {
            validate()
        }
    }

    private fun validate() {
        productName = product_name.text.toString().trim()
        productDescription = product_description.text.toString().trim()
        productPrice = product_price.text.toString().trim()

        when {
            imageUri == null -> {
                toast(this, "Product image is mandatory...")
            }
            TextUtils.isEmpty(productName) -> {
                toast(this, "Please write product name")
            }
            TextUtils.isEmpty(productDescription) -> {
                toast(this, "Please write product description")
            }
            TextUtils.isEmpty(productPrice) -> {
                toast(this, "Please write product price")
            }
            else -> {
                storeProductInfo()
                showLoading(true)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun storeProductInfo() {

        val calendar = Calendar.getInstance()
        val currentDate = SimpleDateFormat("dd MM,yyyy", Locale.getDefault())
        saveCurrentDate = currentDate.format(Date())

        val currentTime = SimpleDateFormat("HH:mm:ss a")
        saveCurrentTime = currentTime.format(calendar.time)

        productRandomKey = "$saveCurrentDate $saveCurrentTime"

        val filePath =
            mStorage.child(imageUri!!.lastPathSegment + productRandomKey + ".jpg")

        val bmp = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos)
        val data: ByteArray = baos.toByteArray()

        val uploadTask = filePath.putBytes(data)
        uploadTask.addOnFailureListener {
            toast(this, "Error: $it")
            showLoading(false)
        }.addOnSuccessListener {
            toast(this, "Image uploaded successfully...")

            val urlTask = uploadTask.continueWithTask {
                if (!it.isSuccessful) {
                    throw it.exception!!
                    showLoading(false)
                }
                downLoadImageUri = filePath.downloadUrl.toString()
                return@continueWithTask filePath.downloadUrl

            }.addOnCompleteListener {
                if (it.isSuccessful) {
                    downLoadImageUri = it.result!!.toString()
                    toast(this, "got the Product image Url Successfully...")
                    saveInfoToDb()
                }
            }
        }
    }

    private fun saveInfoToDb() {
        Log.d(TAG, "saveProductInfoToDb: save to db")
        val productMap: HashMap<String, Any> = HashMap()
        productMap["pid"] = productRandomKey!!
        productMap["date"] = saveCurrentDate
        productMap["description"] = productDescription!!
        productMap["image"] = downLoadImageUri.toString()
        productMap["category"] = category!!
        productMap["price"] = productPrice!!
        productMap["name"] = productName!!

        mDataBaseReference.child(productRandomKey!!).updateChildren(productMap)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    ActivityHelper.start<AdminCategoryActivity>(this)
                    finish()
                    updateInputs()
                    showLoading(false)
                } else {
                    toast(this, "Error: ${it.exception}")
                    showLoading(false)
                }
            }
    }

    private fun updateInputs() {
        product_name.setText("")
        product_description.setText("")
        product_price.setText("")
    }

    private fun showLoading(isLoading: Boolean) {
        product_progress.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}