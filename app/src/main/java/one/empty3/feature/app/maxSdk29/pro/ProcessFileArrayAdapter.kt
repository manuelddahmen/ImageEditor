package one.empty3.feature.app.maxSdk29.pro

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import one.empty3.Main2022
import matrix.PixM
import one.empty3.feature.app.maxSdk29.pro.databinding.RecyclerViewEffectItemBinding
import one.empty3.io.ProcessFile
import java.io.File

class ProcessFileArrayAdapter() :
    RecyclerView.Adapter<ProcessFileArrayAdapter.ViewHolder>(), Parcelable {
    private lateinit var arrayClasses: HashMap<String, ProcessFile>
    private lateinit var activity: ActivitySuperClass
    private lateinit var main2022: Main2022
    private lateinit var rv: RecyclerView

    constructor(parcel: Parcel) : this() {
        var readString: String? = parcel.readString()
        Main2022.effects = ArrayList<String>()
        while (readString != null) {
            Main2022.effects.add(readString.toString())
            readString = parcel.readString()
        }
        arrayClasses = Main2022.initListProcesses()
    }

    fun setMainAnd(main2022: Main2022, rv: RecyclerView, activity2: ChooseEffectsActivity2) {
        this.main2022 = main2022
        this.rv = rv
        this.activity = activity2
        Main2022.effects = ArrayList<String>()
        arrayClasses = Main2022.initListProcesses()
    }

    /*  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
          //super.onCreateViewHolder(parent, viewType)
          //arrayClasses = Main2022.initListProcesses()
          val viewItem = LayoutInflater.from(parent.context)
              .inflate(R.layout.recycler_view_effect_item, parent, false)
          return ViewHolder(viewItem)
      }
  */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(

            RecyclerViewEffectItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var index = ""
        if(position<Main2022.indices.size) {
             index = Main2022.indices!![position]
            val processFile: ProcessFile = arrayClasses[index]!!
            val textView1 = (holder.itemView.findViewById(R.id.textEffectName)) as TextView
            textView1.text = index//processFile.javaClass.name
            val text1 = textView1.text.toString()
            holder.setEffectClass(text1)
            //val text1 = processFile.javaClass.name
            val buttonRemoveList: Button =
                holder.itemView.findViewById(R.id.buttonRemoveFromList) as Button
            val buttonAddToEffect: Button =
                holder.itemView.findViewById(R.id.buttonAddTOEffect) as Button
            val buttonPropsToEffect: Button =
                holder.itemView.findViewById(R.id.commentEffect) as Button
            val seekBar: SeekBar =
                holder.itemView.findViewById(R.id.effectPower) as SeekBar
//val imageViewEffectPreview : ImageView = holder.itemView.findViewById(R.id.imageViewEffectPreview)
            System.out.printf(
                "Layout class is : %s Button1 = %s Button 2 = %s\n",
                text1,
                buttonAddToEffect,
                buttonRemoveList
            )
            if (Main2022.effectsFactors != null && Main2022.effectsFactors.get(text1) != null)
                seekBar.setProgress(Main2022.effectsFactors.get(text1)!!, true);

            if (Main2022.effects.contains(holder.getEffectClass())) {
                buttonAddToEffect.setBackgroundColor(0xaaaaaaaa.toInt())
            } else {
                buttonAddToEffect.setBackgroundColor(0xffaaaaaa.toInt())
            }
            buttonAddToEffect.setOnClickListener {
                val effectClass = holder.getEffectClass()
                val b1 = Main2022.effects.add(effectClass)

                it.setBackgroundColor(0xaaaa0000.toInt())
                /*
                val textView: Unit =
                    ((it as Button).parent as LinearLayout).children.iterator().forEachRemaining {
                        if (it.id.equals(R.id.textEffectName)) {
                            val textView = (it as TextView)
                            val text = it.text.toString()
                            System.err.println("OnClick RecyclerView ProcessFileArrayAdapter text= $text")
                            textView.setBackgroundColor(0xaaaa0000.toInt())
                            Main2022.effects.add(text)
                            activity.classnames.add(text)

                        }
                    }
    */
            }

            buttonRemoveList.setOnClickListener {
                val effectClass = holder.getEffectClass()
                val b1 = Main2022.effects.remove(effectClass)


                if (!b1) {
                    buttonAddToEffect.setBackgroundColor(0xaaaaaaaa.toInt())
                }

//            val textView: Unit =
//                ((it as Button).parent as LinearLayout).children.iterator().forEachRemaining {
//                    if (it.id.equals(R.id.textEffectName)) {
//                        val textView = (it as TextView)
//                        val text = it.text.toString()
//                        System.err.println("OnClick RecyclerView ProcessFileArrayAdapter text= $text")
//                        textView.setBackgroundColor(0xaaaa0000.toInt())
//                        Main2022.effects.remove(text)
//                        activity.classnames.remove(text)
//
//                    }
//                }
            }

            buttonPropsToEffect.setOnClickListener {
                run {
                    val effectClassModele1 = holder.getEffectClass()
                    val processFile11: ProcessFile
                    if (Main2022.listProcessClasses == null)
                        processFile11 =
                            Main2022.initListProcesses()[effectClassModele1] as ProcessFile
                    else
                        processFile11 = Main2022.listProcessClasses[effectClassModele1]!!
                    val p = PixM(30, 30)

                    if (activity.currentFile.currentFile != null) {
                        val runEffectsOnThumbnail: File? =
                            Utils().runEffectsOnThumbnail(activity.currentFile.currentFile, p, processFile11);
                        val imageViewEffectPreview: ImageViewSelection =
                            holder.itemView.findViewById(R.id.imageViewEffectPreview)
                        if ((runEffectsOnThumbnail != null) && runEffectsOnThumbnail.exists())
                            Utils().setImageView(
                                imageViewEffectPreview,
                                one.empty3.ImageIO.read(runEffectsOnThumbnail).getBitmap()
                            )
                    }
                }
            }
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    val effectClass = holder.getEffectClass()
                    Main2022.effectsFactors.put(effectClass, progress)
                    System.err.println("$javaClass $effectClass progress:$progress")
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    // you can probably leave this empty
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    // you can probably leave this empty
                }
            })
        }

    }


    override fun getItemCount(): Int {
        return arrayClasses.size
    }


    class ViewHolder(itemView: RecyclerViewEffectItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {


        private lateinit var effectClass: String

        fun getEffectClass(): String {
            return effectClass
        }

        fun setEffectClass(ec: String) {
            this.effectClass = ec
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        Main2022.effects.forEach {
            parcel.writeString(it)
        }
    }

    override fun describeContents(): Int {
        return arrayClasses.size
    }

    fun setCurrentActivity(chooseEffectsActivity2: ActivitySuperClass) {
        this.activity = chooseEffectsActivity2
    }

    companion object CREATOR : Parcelable.Creator<ProcessFileArrayAdapter> {
        override fun createFromParcel(parcel: Parcel): ProcessFileArrayAdapter {
            return ProcessFileArrayAdapter(parcel)
        }

        override fun newArray(size: Int): Array<ProcessFileArrayAdapter?> {
            return arrayOfNulls(size)
        }
    }


}
