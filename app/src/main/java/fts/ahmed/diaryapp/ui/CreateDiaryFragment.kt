package fts.ahmed.diaryapp.ui

import android.app.*
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import fts.ahmed.diaryapp.databinding.AddNotificationCustomDialogBinding
import fts.ahmed.diaryapp.databinding.FragmentCreateDiaryBinding
import fts.ahmed.diaryapp.pojo.Diary
import fts.ahmed.diaryapp.util.MyNotification
import fts.ahmed.diaryapp.util.channelID
import fts.ahmed.diaryapp.util.notificationID
import java.util.*


@AndroidEntryPoint
class CreateDiaryFragment : Fragment() {

    lateinit var binding: FragmentCreateDiaryBinding
    lateinit var dialogBinding: AddNotificationCustomDialogBinding
    private val viewModel: MyViewModel by viewModels()
    var alarm: Calendar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateDiaryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabSave.bringToFront()
        binding.fabNotify.bringToFront()
        notificationCreationLogic()
        fabSaveClickListener()
        fabNotifyClickListener()
    }

    private fun fabNotifyClickListener() {
        binding.fabNotify.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val text = binding.etTextBody.text.toString()
            if (title.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "please fill the title at least",
                    Toast.LENGTH_SHORT
                ).show()
            } else showCustomDialog()
        }
    }

    private fun showCustomDialog() {
        dialogBinding = AddNotificationCustomDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(dialogBinding.root)
        dialogCustomization(dialog)
        btnSetNotificationClickListener(dialog)
    }

    private fun btnSetNotificationClickListener(dialog: Dialog) {
        dialogBinding.btnSetNotification.setOnClickListener {
            val minute = dialogBinding.timePicker.minute
            val hour = dialogBinding.timePicker.hour
            val day = dialogBinding.datePicker.dayOfMonth
            val month = dialogBinding.datePicker.month
            val year = dialogBinding.datePicker.year
            alarm = Calendar.getInstance()
            alarm.let { alarm ->
                alarm?.set(year, month, day, hour, minute)
            }
            scheduleNotification(alarm!!.timeInMillis)
            Toast.makeText(requireContext(), "notification added", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
    }

    private fun scheduleNotification(time:Long) {
        val pendingIntent = createPendingIntent()
        val alarmManager=requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )

    }

    private fun createPendingIntent(): PendingIntent {
        // usual intent
        val intent = Intent(requireContext(), MyNotification::class.java)
        intent.putExtra("titleExtra", binding.etTitle.text.toString())
        intent.putExtra("messageExtra", binding.etTextBody.text.toString())
        // pending intent
        return PendingIntent.getBroadcast(
            requireContext(),
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun notificationCreationLogic() {
        val channel = createNotificationChannel()
        val notificationManager =
            requireContext().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotificationChannel(): NotificationChannel {
        val channel = NotificationChannel(
            channelID,
            "dairy schedule",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "this notification specified by the user"
        return channel
    }

    private fun dialogCustomization(dialog: Dialog) {
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.getWindow()?.getAttributes())
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog.show()
        dialog.window?.attributes = lp
    }

    private fun fabSaveClickListener() {
        binding.fabSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val text = binding.etTextBody.text.toString()
            if (title.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "please fill the title at least",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.insert(Diary(title = title, text = text, alarm = alarm?.timeInMillis))
                Toast.makeText(requireContext(), "saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

}

