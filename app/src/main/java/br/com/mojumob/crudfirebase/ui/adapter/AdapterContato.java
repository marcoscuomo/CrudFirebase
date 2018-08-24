package br.com.mojumob.crudfirebase.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import br.com.mojumob.crudfirebase.R;
import br.com.mojumob.crudfirebase.model.Contato;

public class AdapterContato extends RecyclerView.Adapter<AdapterContato.MyViewHolder> {

   private Context context;
   private List<Contato> listaContatos;

    public AdapterContato(Context context, List<Contato> listaContatos) {
        this.context = context;
        this.listaContatos = listaContatos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View item = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_contatos, viewGroup, false);


        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        Contato contato = listaContatos.get(i);
        String nome = contato.getNome();
        String email = contato.getEmail();
        String telefone = contato.getTelfone();
        holder.txtNome.setText(nome);
        holder.txtTelefone.setText(telefone);
        holder.txtEmail.setText(email);

        if(!(email.equals("")) && !(telefone.equals(""))){
            holder.imgContato.setImageResource(R.drawable.ic_contato_cinza_24dp);
        }else if(!(email.equals("")) && (telefone.equals(""))){
            holder.imgContato.setImageResource(R.drawable.ic_email_azul_24dp);
        }else if((email.equals("")) && !(telefone.equals(""))){
            holder.imgContato.setImageResource(R.drawable.ic_telefone_rosa_24dp);
        }



    }

    @Override
    public int getItemCount() {
        return listaContatos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imgContato;
        TextView txtNome, txtTelefone, txtEmail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgContato  = itemView.findViewById(R.id.adapter_imgContato);
            txtNome     = itemView.findViewById(R.id.adapter_txtNome);
            txtEmail    = itemView.findViewById(R.id.adapter_txtEmail);
            txtTelefone = itemView.findViewById(R.id.adapter_txtTelefone);

        }
    }
}
