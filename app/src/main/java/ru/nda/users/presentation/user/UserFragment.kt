package ru.nda.users.presentation.user

import android.R.drawable.stat_notify_error
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.util.Linkify
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.load
import coil.transform.CircleCropTransformation
import ru.nda.users.databinding.FragmentUserBinding
import ru.nda.users.domain.entity.User
import ru.nda.users.presentation.App
import ru.nda.users.presentation.vmfactory.ViewModelFactory
import ru.nda.users.utils.appendLine
import java.util.regex.Matcher
import javax.inject.Inject


class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentUserBinding is null")

    @Inject
    lateinit var imageLoader: ImageLoader

    private var _user: User? = null
    private val user
        get() = _user ?: throw RuntimeException("User is null")

    private val component by lazy {
        (requireActivity().application as App).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val userViewModel by lazy {
        ViewModelProvider.create(this, viewModelFactory)[UserViewModel::class]
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        arguments?.let { bundle ->
            _user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(USER, User::class.java)
            } else {
                bundle.getParcelable(USER)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUserInformation()

    }

    private fun setupUserInformation() {
        with(binding) {
            photoIv.load(user.picture.large, imageLoader) {
                error(stat_notify_error)
                transformations(CircleCropTransformation())
            }
            commonInformationTv.text = buildSpannedString {
                appendLine("Name", listOf(user.name.firstName, user.name.lastName))
                val phone = SpannableString(user.phone)
                Linkify.addLinks(
                    phone,
                    Patterns.PHONE,
                    "tel:",
                    Linkify.sPhoneNumberMatchFilter,
                    Linkify.sPhoneNumberTransformFilter
                )
                append("Phone: ")
                append(phone)
                append(System.lineSeparator())
                appendLine("Gender", listOf(user.gender))
                appendLine("Date of birth", listOf(user.dob.date))
                appendLine("Age", listOf(user.dob.age))
                val email = SpannableString(user.email)
                Linkify.addLinks(
                    email,
                    Patterns.EMAIL_ADDRESS,
                    "mailto:",
                    null,
                    object : Linkify.TransformFilter {
                        override fun transformUrl(
                            match: Matcher?,
                            url: String?
                        ): String? {
                            return url
                        }
                    }
                )
                append("Email: ")
                append(email)
                append(System.lineSeparator())
            }
            commonInformationTv.movementMethod = LinkMovementMethod.getInstance()
            loginTv.text = buildSpannedString {
                appendLine("Username", listOf(user.login.username))
                appendLine("Password", listOf(user.login.password))
            }
            locationTv.text = buildSpannedString {
                appendLine(
                    "Street",
                    listOf(user.location.street.name, user.location.street.number),
                    separator = ", "
                )
                appendLine("City", listOf(user.location.city))
                appendLine("State", listOf(user.location.state))
                appendLine("Country", listOf(user.location.country))
                appendLine("Postcode", listOf(user.location.postcode))
                val coordinates =
                    SpannableString("${user.location.coordinates.latitude},${user.location.coordinates.longitude}")
                coordinates.setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        userViewModel.showMap(
                            latitude = user.location.coordinates.latitude,
                            longitude = user.location.coordinates.longitude
                        )
                    }
                }, 0, coordinates.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                append("Coordinates: ")
                append(coordinates)
                append(System.lineSeparator())
                appendLine(
                    "Timezone",
                    listOf(user.location.timezone.description, user.location.timezone.offset),
                    separator = ", "
                )
            }
            locationTv.movementMethod = LinkMovementMethod.getInstance()
            registeredTv.text = buildSpannedString {
                appendLine("Date", listOf(user.registered.date))
                appendLine("Age", listOf(user.registered.age))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        //        private const val ITEM_CLASS_NAME = "ru.nda.users.domain.entity.User"
        private const val USER = "user"

        fun newInstance(user: User) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER, user)
//                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
//                        classLoader =
//                            ClassLoader.getSystemClassLoader()
//                                .loadClass(ITEM_CLASS_NAME).classLoader
//                    }
                }
            }
    }
}