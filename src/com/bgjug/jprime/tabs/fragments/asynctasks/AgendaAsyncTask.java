package com.bgjug.jprime.tabs.fragments.asynctasks;

import java.util.List;

import com.bgjug.jprime.model.Session;
import com.bgjug.jprime.rest.RestClient;
import com.bgjug.jprime.tabs.fragments.AgendaFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class AgendaAsyncTask extends AsyncTask<String, Void, List<Session>> {

	private AgendaFragment agendaActivity;
	private ProgressDialog progressD;
	private RestClient jPrimeRC;
	private List<Session> sessions;

	public AgendaAsyncTask(AgendaFragment agendaActivity) {
		this.agendaActivity = agendaActivity;
		this.jPrimeRC = new RestClient();
	}

	@Override
	protected void onPreExecute() {
		progressD = new ProgressDialog(agendaActivity.getActivity());
		progressD.setTitle("Loading jPrime Agenda");
		progressD.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		progressD.show();
	}

	@Override
	protected List<Session> doInBackground(String... params) {
		if (sessions == null)
			sessions = jPrimeRC.getSessions();
		return sessions;
	}

	@Override
	protected void onCancelled() {
		if (progressD != null) {
			progressD.dismiss();
		}
	}

	@Override
	protected void onPostExecute(List<Session> result) {
		if (progressD != null) {
			progressD.dismiss();
		}

		if (result != null) {
			agendaActivity.loadAgenda(result, 1);
		}
	}

}