from property import *

# Neuron common parameters

###########these are for iaf. do i change them, cause hh?###############
hh_neuronparams = {'E_L': -70.,
                    'V_th': -50.,
                    'V_reset': -67.,
                    'C_m': 2.,
                    't_ref': 2.,
                    'V_m': -60.,
                    'tau_syn_ex': 1.,
                    'tau_syn_in': 1.33}

# Synapse common parameters
STDP_synapseparams = {
    'model': 'stdp_synapse',
    'tau_m': {'distribution': 'uniform', 'low': 15., 'high': 25.},
    'alpha': {'distribution': 'normal_clipped', 'low': 0.5, 'mu': 5.0, 'sigma': 1.0},
    'delay': {'distribution': 'uniform', 'low': 0.8, 'high': 2.5},
    'lambda': 0.5
}
# Glutamate synapse
STDP_synparams_Glu = dict({'delay': {'distribution': 'uniform', 'low': 1, 'high': 1.3},
                           'weight': w_Glu,
                           'Wmax': 70.}, **STDP_synapseparams)
# GABA synapse
STDP_synparams_GABA = dict({'delay': {'distribution': 'uniform', 'low': 1., 'high': 1.3},
                            'weight': w_GABA,
                            'Wmax': -60.}, **STDP_synapseparams)
# Acetylcholine synapse
STDP_synparams_ACh = dict({'delay': {'distribution': 'uniform', 'low': 1, 'high': 1.3},
                           'weight': w_ACh,
                           'Wmax': 70.}, **STDP_synapseparams)

#########################################NOW DIFFERENT, CAUSE NA? OR SAME?###################
# Dopamine synapse common parameter
NORA_synparams = {'delay': 1.}
# Dopamine exhibitory synapse
NORA_synparams_ex = dict({'weight': w_NR_ex,
                          'Wmax': 100.,
                          'Wmin': 85.}, **NORA_synparams)
# Dopamine inhibitory synapse

NORA_synparams_in = dict({'weight': w_NR_in,
                          'Wmax': -100.,
                          'Wmin': -85.}, **NORA_synparams)

# Create volume transmitters

# Dictionary of synapses with keys and their parameters
types = {GABA: (STDP_synparams_GABA, w_GABA, 'GABA'),
         ACh: (STDP_synparams_ACh, w_ACh, 'Ach'),
         Glu: (STDP_synparams_Glu, w_Glu, 'Glu'),
         NA_ex: (NORA_synparams_ex, w_NR_ex, 'NA_ex', nora_model_ex),
         NA_in: (NORA_synparams_in, w_NR_in, 'NA_in', nora_model_in)}

# Parameters for generator links
static_syn = {
    'model': 'static_synapse',
    'weight': w_Glu * 5,
    'delay': pg_delay
}

# Connection parameters
conn_dict = {'rule': 'all_to_all',
             'multapses': True}

# Device parameters
multimeter_param = {'to_memory': True,
                    'to_file': False,
                    'withtime': True,
                    'interval': 0.1,
                    'record_from': ['V_m'],
                    'withgid': True}

detector_param = {'label': 'spikes',
                  'withtime': True,
                  'withgid': True,
                  'to_file': False,
                  'to_memory': True,
'scientific': True}