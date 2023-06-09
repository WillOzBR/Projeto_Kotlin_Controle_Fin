package dev.spikeysanju.expensetracker.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.spikeysanju.expensetracker.R
import dev.spikeysanju.expensetracker.databinding.ItemTransactionLayoutBinding
import dev.spikeysanju.expensetracker.model.Transaction
import indianRupee

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.TransactionVH>() {

    inner class TransactionVH(val binding: ItemTransactionLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionVH {
        val binding =
            ItemTransactionLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionVH(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TransactionVH, position: Int) {

        val item = differ.currentList[position]
        holder.binding.apply {

            transactionName.text = item.title
            transactionCategory.text = item.tag

            when (item.transactionType) {
                "Gasto" -> {
                    transactionAmount.setTextColor(
                        ContextCompat.getColor(
                            transactionAmount.context,
                            R.color.expense
                        )
                    )

                    transactionAmount.text = "- ".plus(indianRupee(item.amount))
                }
                "Ganho" -> {
                    transactionAmount.setTextColor(
                        ContextCompat.getColor(
                            transactionAmount.context,
                            R.color.income
                        )
                    )
                    transactionAmount.text = "+ ".plus(indianRupee(item.amount))
                }
            }

            when (item.tag) {
                "Casa" -> {
                    transactionIconView.setImageResource(R.drawable.ic_food)
                }
                "Transporte" -> {
                    transactionIconView.setImageResource(R.drawable.ic_transport)
                }
                "Comida" -> {
                    transactionIconView.setImageResource(R.drawable.ic_food)
                }
                "Utilidades" -> {
                    transactionIconView.setImageResource(R.drawable.ic_utilities)
                }
                "Seguro" -> {
                    transactionIconView.setImageResource(R.drawable.ic_insurance)
                }
                "Saúde" -> {
                    transactionIconView.setImageResource(R.drawable.ic_medical)
                }
                "Salvos & Debitos" -> {
                    transactionIconView.setImageResource(R.drawable.ic_savings)
                }
                "Gastos Pessoais" -> {
                    transactionIconView.setImageResource(R.drawable.ic_personal_spending)
                }
                "Entretenimento" -> {
                    transactionIconView.setImageResource(R.drawable.ic_entertainment)
                }
                "Diversos" -> {
                    transactionIconView.setImageResource(R.drawable.ic_others)
                }
                else -> {
                    transactionIconView.setImageResource(R.drawable.ic_others)
                }
            }

            // on item click
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
        }
    }

    // on item click listener
    private var onItemClickListener: ((Transaction) -> Unit)? = null
    fun setOnItemClickListener(listener: (Transaction) -> Unit) {
        onItemClickListener = listener
    }
}
